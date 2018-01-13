package repositories

import java.sql.Timestamp
import javax.inject.Inject

import models.User
import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class UserRepository @Inject()(dbConfigProvider: DatabaseConfigProvider, repositoryUtils: RepositoryUtils)(
    implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._
  import repositoryUtils.dateTimeMapper

  private class UserTable(tag: Tag) extends Table[User](tag, "user") {

    // TODO: what the hell does slick do with this information? why is AutoInc useful?
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def firstName = column[String]("first_name")

    def lastName = column[String]("last_name")

    def email = column[String]("email")

    def createdAt = column[DateTime]("created_at")

    def updatedAt = column[DateTime]("updated_at")

    def * =
      (id, firstName, lastName, email, createdAt, updatedAt) <> ((User.apply _).tupled, User.unapply)

  }

  private val users = TableQuery[UserTable]

  def list(): Future[Seq[User]] = db.run {
    users.result
  }

  def get(id: Long): Future[Option[User]] =
    db.run {
        users.filter(_.id === id).result
      }
      .map(_.headOption)

  def insert(firstName: String, lastName: String, email: String): Future[User] =
    db.run {
      val currentTime = DateTime.now()
      (users.map(x => (x.firstName, x.lastName, x.email, x.createdAt, x.updatedAt))
        returning users.map(_.id)
        into ((stuff, id) => User(id, stuff._1, stuff._2, stuff._3, stuff._4, stuff._5))
        ) += (firstName, lastName, email, currentTime, currentTime)
    }

}
