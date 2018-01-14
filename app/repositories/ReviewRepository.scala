package repositories

import javax.inject.Inject

import models.Review
import models.ReviewStatistic
import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.collection.immutable
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class ReviewRepository @Inject()(
  dbConfigProvider: DatabaseConfigProvider,
  repositoryUtils: RepositoryUtils)(
  implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import ReviewRepository._
  import dbConfig._
  import profile.api._
  import repositoryUtils.dateTimeMapper

  private class ReviewTable(tag: Tag) extends Table[Review](tag, "review") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def cardId = column[Long]("card_id")

    def userId = column[Long]("user_id")

    def englishResponse = column[String]("english_response")

    def japaneseResponse = column[String]("japanese_response")

    def englishCorrect = column[Boolean]("english_correct")

    def japaneseCorrect = column[Boolean]("japanese_correct")

    def createdAt = column[DateTime]("created_at")

    def * =
      (id,
       cardId,
       userId,
       englishResponse,
       japaneseResponse,
       englishCorrect,
       japaneseCorrect,
       createdAt) <> ((Review.apply _).tupled, Review.unapply)

  }

  // TODO: figure out a better way to do this
  private class ReviewStatisticTable(tag: Tag) extends Table[ReviewStatistic](tag, "review_statistics") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def cardId = column[Long]("card_id")

    def userId = column[Long]("user_id")

    def nextReview = column[DateTime]("next_review")

    def level = column[Int]("level")

    def maxLevel = column[Int]("max_level")

    def createdAt = column[DateTime]("created_at")

    def updatedAt = column[DateTime]("updated_at")

    def * =
      (id, cardId, userId, nextReview, level, maxLevel, createdAt, updatedAt) <> ((ReviewStatistic.apply _).tupled, ReviewStatistic.unapply)

  }

  private val reviews = TableQuery[ReviewTable]
  private val reviewStatistics = TableQuery[ReviewStatisticTable]

  def list(): Future[Seq[Review]] = db.run {
    reviews.result
  }

  def get(id: Long): Future[Option[Review]] = db.run {
    reviews.filter(_.id === id).result
  }.map(_.headOption)

  /**
    * This method will insert a review as well as updated the review statistic (within a transaction).
    */
  def insert(
      cardId: Long,
      userId: Long,
      englishResponse: String,
      japaneseResponse: String,
      englishCorrect: Boolean,
      japaneseCorrect: Boolean): Future[Review] =  {

    val currentTime = DateTime.now()

    val insertReview = (reviews.map(x => (x.cardId, x.userId, x.englishResponse, x.japaneseResponse, x.englishCorrect, x.japaneseCorrect, x.createdAt))
      returning reviews.map(_.id)
      into ((stuff, id) => Review(id, stuff._1, stuff._2, stuff._3, stuff._4, stuff._5, stuff._6, stuff._7))
      ) += (cardId, userId, englishResponse, japaneseResponse, englishCorrect, japaneseCorrect, currentTime)

    val actions = for {
      review <- insertReview
      stats <- reviewStatistics.filter(x => x.userId === userId && x.cardId === cardId).result
      statOption = stats.headOption

      // If the entry already exists, update it accordingly.
      _ <- statOption.map { stat =>
        // Pick the row and what columns
        val query = for { x <- reviewStatistics if x.id === stat.id } yield (x.nextReview, x.level, x.maxLevel)


        if (review.isCorrect()) {
          val nextLevel = math.min(stat.level + 1, NUM_LEVELS)
          query.update(
            (currentTime.plusHours(LEVEL_TIMINGS(nextLevel)),
              nextLevel,
              math.max(nextLevel, stat.maxLevel)))
        } else {
          val nextLevel = math.max(0, stat.level - 2)
          query.update(
            (currentTime.plusHours(LEVEL_TIMINGS(nextLevel)),
              nextLevel,
              math.max(nextLevel, stat.maxLevel)))
        }

      // Otherwise, add a new entry to the table
      }.getOrElse {
        (reviewStatistics.map(x => (x.cardId, x.userId, x.nextReview, x.level, x.maxLevel, x.createdAt, x.updatedAt))
          returning reviewStatistics.map(_.id)
          into ((stuff, id) => ReviewStatistic(id, stuff._1, stuff._2, stuff._3, stuff._4, stuff._5, stuff._6, stuff._7))
          ) += (cardId, userId, currentTime.plusHours(LEVEL_TIMINGS(1)), 1, 1, currentTime, currentTime)
      }
    } yield {
      review
    }

    db.run(actions.transactionally)
  }

}

object ReviewRepository {

  /**
    * Timings for how long we should wait until the next level (in hours)
    * NOTE: this cannot be changed, and it'll probably cause some chaos if you do.
    */
  final val LEVEL_TIMINGS: immutable.Seq[Int] = List(
    0,            // 0 hours
    4,            // 4 hours
    8,            // 8 hours
    24 * 1,       // 1 day
    24 * 2,       // 2 days
    24 * 7,       // 1 week
    24 * 14,      // 2 weeks
    24 * 30,      // 1 month
    24 * 30 * 4   // 2 months
  )

  final val NUM_LEVELS = LEVEL_TIMINGS.length + 1
}
