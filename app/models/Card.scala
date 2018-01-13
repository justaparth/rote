package models

import org.joda.time.DateTime
import play.api.libs.json.Json
import play.api.libs.json.OFormat

import JodaDateTimeHelper.jodaDateReads
import JodaDateTimeHelper.jodaDateWrites

case class Card(
  id: Long,
  japanese: String,
  furigana: String,
  english: Seq[String],
  notes: String,
  createdAt: DateTime,
  updatedAt: DateTime
)

object Card {
  implicit val format: OFormat[Card] = Json.format[Card]
}
