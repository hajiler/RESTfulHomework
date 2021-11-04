import Homework3.*
import org.scalatest.PrivateMethodTester
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import collection.JavaConverters._

class LambdaTest extends AnyFlatSpec with Matchers with PrivateMethodTester {
  behavior of "Lambda util functions"

  it should "Parse an event" in {
    val event = Map(
      "time" -> "10:32:54:555",
      "deltaHour" -> "0",
      "deltaMinute" -> "0",
      "deltaSecond" -> "0",
      "deltaMillisecond" -> "100")

    val actual = parseTime(event.asJava)
    actual._1 shouldBe "10:32:54:555"
    actual._2 shouldBe "0:0:0:100"
  }
}
