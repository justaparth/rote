package repositories

import javax.inject.Inject

import models.Deck
import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class DeckRepository @Inject()(dbConfigProvider: DatabaseConfigProvider, repositoryUtils: RepositoryUtils)(
  implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._
  import repositoryUtils.dateTimeMapper

  private class DeckTable(tag: Tag) extends Table[Deck](tag, "deck") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def createdAt = column[DateTime]("created_at")

    def updatedAt = column[DateTime]("updated_at")

    def * =
      (id, name, createdAt, updatedAt) <> ((Deck.apply _).tupled, Deck.unapply)

  }

  private val decks = TableQuery[DeckTable]

  def list(): Future[Seq[Deck]] =
    db.run {
      decks.result
    }

  def get(id: Long): Future[Option[Deck]] = {
    db.run {
      decks.filter(_.id === id).result
    }
      .map { result =>
        result.headOption
      }
  }

  def insert(name: String): Future[Deck] = db.run {
    val currentTime = DateTime.now()
    (decks.map(x => (x.name, x.createdAt, x.updatedAt))
      returning decks.map(_.id)
      into ((stuff, id) => Deck(id, stuff._1, stuff._2, stuff._3))
      ) += (name, currentTime, currentTime)
  }

}
