package models

import org.joda.time.DateTime
import play.api.libs.json.JsNumber
import play.api.libs.json.JsValue
import play.api.libs.json.Reads
import play.api.libs.json.Writes

object JodaDateTimeHelper {
  implicit val jodaDateReads: Reads[DateTime] = Reads[DateTime](js =>
    js.validate[Long].map(x => new DateTime(x))
  )

  implicit val jodaDateWrites: Writes[DateTime] = new Writes[DateTime] {
    override def writes(o: DateTime): JsValue = JsNumber(BigDecimal.valueOf(o.getMillis))
  }
}
