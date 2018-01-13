package repositories

import javax.inject.Inject

import models.Card
import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class CardRepository @Inject()(dbConfigProvider: DatabaseConfigProvider, repositoryUtils: RepositoryUtils)(
    implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._
  import repositoryUtils.dateTimeMapper

  /**
    * Mapper for java.sql.Timestamp to joda.org.time.DateTime
    * TODO: this is nowhere near robust enough!
    * TODO: test.
    */
  private implicit val stringSequenceMapper = MappedColumnType.base[Seq[String], String](
    /* f: Seq[String] -> String */
    seq => seq.mkString(","),
    /* g: String -> Seq[String] */
    str  => str.split(",")
  )

  private class CardTable(tag: Tag) extends Table[Card](tag, "card") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def japanese = column[String]("japanese")

    def furigana = column[String]("furigana")

    def english = column[Seq[String]]("english")

    def notes = column[String]("notes")

    def createdAt = column[DateTime]("created_at")

    def updatedAt = column[DateTime]("updated_at")

    def * =
      (id, japanese, furigana, english, notes, createdAt, updatedAt) <> ((Card.apply _).tupled, Card.unapply)

  }

  private val cards = TableQuery[CardTable]

  def list(): Future[Seq[Card]] =
    db.run {
      cards.result
    }

  def get(id: Long): Future[Option[Card]] = {
    db.run {
        cards.filter(_.id === id).result
      }
      .map { result =>
        result.headOption
      }
  }

}
