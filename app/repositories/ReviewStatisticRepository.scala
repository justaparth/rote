package repositories

import javax.inject.Inject

import models.Deck
import models.ReviewStatistic
import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class ReviewStatisticRepository @Inject()(dbConfigProvider: DatabaseConfigProvider, repositoryUtils: RepositoryUtils)(
  implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._
  import repositoryUtils.dateTimeMapper

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

  private val reviewStatistics = TableQuery[ReviewStatisticTable]

  def list(): Future[Seq[ReviewStatistic]] =
    db.run {
      reviewStatistics.result
    }

  def get(id: Long): Future[Option[ReviewStatistic]] = {
    db.run {
      reviewStatistics.filter(_.id === id).result
    }
      .map { result =>
        result.headOption
      }
  }

  def insert(cardId: Long, userId: Long, nextReview: DateTime, level: Int, maxLevel: Int): Future[ReviewStatistic] = db.run {
    val currentTime = DateTime.now()
    (reviewStatistics.map(x => (x.cardId, x.userId, x.nextReview, x.level, x.maxLevel, x.createdAt, x.updatedAt))
      returning reviewStatistics.map(_.id)
      into ((stuff, id) => ReviewStatistic(id, stuff._1, stuff._2, stuff._3, stuff._4, stuff._5, stuff._6, stuff._7))
      ) += (cardId, userId, nextReview, level, maxLevel, currentTime, currentTime)
  }

}
