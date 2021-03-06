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
    seq => seq.mkString("~~,~~"),
    /* g: String -> Seq[String] */
    str  => str.split(",")
  )

  private class CardTable(tag: Tag) extends Table[Card](tag, "card") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def deckId = column[Long]("deck_id")

    def japanese = column[String]("japanese")

    def furigana = column[String]("furigana")

    def english = column[Seq[String]]("english")

    def notes = column[String]("notes")

    def createdAt = column[DateTime]("created_at")

    def updatedAt = column[DateTime]("updated_at")

    def * =
      (id, deckId, japanese, furigana, english, notes, createdAt, updatedAt) <> ((Card.apply _).tupled, Card.unapply)

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

  def insert(deckId: Long, japanese: String, furigana: String, english: Seq[String], notes: String): Future[Card] = db.run {
    val currentTime = DateTime.now()
    (cards.map(x => (x.japanese, x.furigana, x.english, x.notes, x.createdAt, x.updatedAt))
      returning cards.map(_.id)
      into ((stuff, id) => Card(id, deckId, stuff._1, stuff._2, stuff._3, stuff._4, stuff._5, stuff._6))
      ) += (japanese, furigana, english, notes, currentTime, currentTime)
  }

}
