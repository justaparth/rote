package models

import org.joda.time.DateTime
import play.api.libs.json.Json
import play.api.libs.json.OFormat

import JodaDateTimeHelper.jodaDateWrites
import JodaDateTimeHelper.jodaDateReads

case class Review(
  id: Long,
  cardId: Long,
  userId: Long,
  englishResponse: String,
  japaneseResponse: String,
  englishCorrect: Boolean,
  japaneseCorrect: Boolean,
  createdAt: DateTime
) {

  def isCorrect(): Boolean = {
    englishCorrect && japaneseCorrect
  }

}

object Review {
  implicit val format: OFormat[Review] = Json.format[Review]
}