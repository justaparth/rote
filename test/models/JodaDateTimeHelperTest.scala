package models

import org.joda.time.DateTime
import org.scalatest.FlatSpec
import play.api.libs.json.JsNumber

class JodaDateTimeHelperTest extends FlatSpec {

  "Reads" should "read timestamps correctly" in {
    val expected = DateTime.now()
    val result = JodaDateTimeHelper.jodaDateReads.reads(JsNumber(BigDecimal.valueOf(expected.getMillis)))
    System.out.println(result)
    assert(result.get == expected)
  }

  "Writes" should "correctly output a value" in {
    val expected = DateTime.now()
    val result = JodaDateTimeHelper.jodaDateWrites.writes(expected)
    System.out.println(result)
    assert(result == JsNumber(BigDecimal.valueOf(expected.getMillis)))
  }

}
