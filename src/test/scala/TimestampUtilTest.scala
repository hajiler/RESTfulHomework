import Homework3.*
import org.scalatest.PrivateMethodTester
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TimestampUtilTest extends AnyFlatSpec with Matchers with PrivateMethodTester {
  behavior of "Timestamp Utility functions"

  it should "Correctly comparing two string timestamps" in {
    val time1 = "16:28:44.276"
    val time2 = "16:28:44.352"
    TimestampUtil.compareTimestamps(time1, time1) shouldBe true
    TimestampUtil.compareTimestamps(time1, time2) shouldBe true
    TimestampUtil.compareTimestamps(time2, time1) shouldBe false
    TimestampUtil.compareTimestamps("16:28:44.276", "17:28:0:0")
  }

  it should "Correctly create a new timestamp by adding a duration in the format hh:mm:ss:ms to a timestamp" in {
    val timestamp = "16:28:44.276"
    val duration1 = "0:0:11:345"
    val duration2 = "4:0:2:1"
    TimestampUtil.addDurationToTime(timestamp, duration1) shouldBe "16:28:55:621"
    TimestampUtil.addDurationToTime(timestamp, duration2) shouldBe "20:28:46:277"
  }
}
