package repositories

import javax.inject.Inject

import models.Review
import org.joda.time.DateTime
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

  private val reviews = TableQuery[ReviewTable]

  def list(): Future[Seq[Review]] = db.run {
    reviews.result
  }

}
