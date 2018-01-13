package repositories

import java.sql.Timestamp
import javax.inject.Inject

import models.Card
import models.User
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class CardRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(
    implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private case class StoredCard(
      id: Long,
      japanese: String,
      furigana: String,
      english: String,
      notes: String,
      createdAt: Timestamp,
      updatedAt: Timestamp
  )

  // TODO: theres probably a better way to do this
  implicit def storedCardToCard(storedCard: StoredCard): Card = {
    new Card(
      id = storedCard.id,
      japanese = storedCard.japanese,
      furigana = storedCard.furigana,
      english = storedCard.english.split(","),
      notes = storedCard.notes,
      createdAt = storedCard.createdAt,
      updatedAt = storedCard.updatedAt
    )
  }

  implicit def storedCardSeqToCard(seq: Seq[StoredCard]): Seq[Card] = {
    seq.map(storedCard => storedCardToCard(storedCard))
  }

  private class CardTable(tag: Tag) extends Table[StoredCard](tag, "card") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def japanese = column[String]("japanese")

    def furigana = column[String]("furigana")

    def english = column[String]("english")

    def notes = column[String]("notes")

    def createdAt = column[Timestamp]("created_at")

    def updatedAt = column[Timestamp]("updated_at")

    def * =
      (id, japanese, furigana, english, notes, createdAt, updatedAt) <> ((StoredCard.apply _).tupled, StoredCard.unapply)

  }

  private val cards = TableQuery[CardTable]

  def list(): Future[Seq[Card]] =
    db.run {
        cards.result
      }
      .map { x =>
        x
      }

  def get(id: Long): Future[Option[Card]] = {
    db.run {
        cards.filter(_.id === id).result
      }
      .map { result =>
        result.headOption.map(x => x)
      }
  }

}
