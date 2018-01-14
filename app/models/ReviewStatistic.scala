package models

import org.joda.time.DateTime
import play.api.libs.json.Json
import play.api.libs.json.OFormat

import JodaDateTimeHelper.jodaDateWrites
import JodaDateTimeHelper.jodaDateReads

case class ReviewStatistic(
  id: Long,
  cardId: Long,
  userId: Long,
  nextReview: DateTime,
  level: Int,
  maxLevel: Int,
  createdAt: DateTime,
  updatedAt: DateTime)

object ReviewStatistic {
  implicit val format: OFormat[ReviewStatistic] = Json.format[ReviewStatistic]
}
