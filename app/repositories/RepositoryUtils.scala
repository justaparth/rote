package repositories

import java.sql.Timestamp
import javax.inject.Inject

import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

class RepositoryUtils @Inject() (dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig.profile.api._

  /**
    * Mapper for java.sql.Timestamp to joda.org.time.DateTime
    */
  implicit val dateTimeMapper = MappedColumnType.base[DateTime, Timestamp](
    /* f: DateTime -> Timestamp */
    dt => new Timestamp(dt.getMillis),
    /* g: Timestamp -> DateTime */
    t  => new DateTime(t.getTime)
  )

}
