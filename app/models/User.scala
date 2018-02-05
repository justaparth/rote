package models

import org.joda.time.DateTime
import JodaDateTimeHelper.jodaDateWrites
import JodaDateTimeHelper.jodaDateReads
import play.api.libs.json.Json
import play.api.libs.json.OFormat

// TODO: serialize user ids as their own type.
case class User(
  id: Long,
  firstName: String,
  lastName: String,
  email: String,
  createdAt: DateTime,
  updatedAt: DateTime
)

object User {
  implicit val format: OFormat[User] = Json.format[User]
}
