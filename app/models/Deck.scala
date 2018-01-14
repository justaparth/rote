package models

import org.joda.time.DateTime
import play.api.libs.json.Json
import play.api.libs.json.OFormat

import JodaDateTimeHelper.jodaDateReads
import JodaDateTimeHelper.jodaDateWrites

// TODO: should these be associated with users?
case class Deck(id: Long, name: String, createdAt: DateTime, updatedAt: DateTime)

object Deck {
  implicit val format: OFormat[Deck] = Json.format[Deck]
}