package repositories

import javax.inject.Inject

import models.Review
import models.ReviewStatistic
import org.joda.time.DateTime
import org.joda.time.ReadableDuration
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class ReviewRepository @Inject()(
  dbConfigProvider: DatabaseConfigProvider,
  repositoryUtils: RepositoryUtils)(
  implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

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

    val finalQuery = insertReview.flatMap { insertRes =>

      // try and get the damn review statistic
      val derp = reviewStatistics.filter(x => x.userId === userId && x.cardId === cardId).result
      derp.flatMap { stats: Seq[ReviewStatistic] =>

        val statOption = stats.headOption
        statOption.map { stat =>
          val query = for { x <- reviewStatistics if x.id === stat.id } yield (x.nextReview, x.level, x.maxLevel)
          query.update((DateTime.now().plusHours(4), stat.level + 1, math.max(stat.level + 1, stat.maxLevel))).map(_ => insertRes)
        }.getOrElse {
          // do normal addition here with defaults!
          val currentTime = DateTime.now()
          ((reviewStatistics.map(x => (x.cardId, x.userId, x.nextReview, x.level, x.maxLevel, x.createdAt, x.updatedAt))
            returning reviewStatistics.map(_.id)
            into ((stuff, id) => ReviewStatistic(id, stuff._1, stuff._2, stuff._3, stuff._4, stuff._5, stuff._6, stuff._7))
            ) += (cardId, userId, DateTime.now().plusHours(1), 1, 1, currentTime, currentTime)).map(_ => insertRes)
        }
      }
    }

    db.run(finalQuery.transactionally)
  }

}
