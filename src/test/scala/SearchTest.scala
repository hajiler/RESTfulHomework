import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.PrivateMethodTester
import org.scalatest.matchers.should.Matchers
import Homework3._

class LambdaTest extends AnyFlatSpec with Matchers with PrivateMethodTester {
  behavior of "AWS Lambda and search functionality"
  val testLogFile =
    "16:28:44.276 [scala-execution-context-global-123] WARN  HelperUtils.Parameters$ - A><YFqpg+~\"E1T\n" +
    "16:28:44.305 [scala-execution-context-global-123] DEBUG HelperUtils.Parameters$ - JrQB;P0\"&+6;&Dk-\n" +
    "16:28:44.314 [scala-execution-context-global-123] INFO  HelperUtils.Parameters$ - OsI1`qAeU5H;\\+\n" +
    "16:28:44.331 [scala-execution-context-global-123] INFO  HelperUtils.Parameters$ - [h!Q9PEY>L(NpKLBO\"Gjo:=4kRXQ_tZ!\n" +
    "16:28:44.352 [scala-execution-context-global-123] INFO  HelperUtils.Parameters$ - B?y&C\"C5rsb:2037;f&|vM#x?z|Ny|&<44Z8B&rF1#&M\n" +
    "16:28:44.370 [scala-execution-context-global-123] WARN  HelperUtils.Parameters$ - x2oBSI0/\\%CdfV2%ChSsnZ7vJo=2qJqZ%.\"kbc!0ne`y&m\n" +
    "16:28:44.389 [scala-execution-context-global-123] ERROR HelperUtils.Parameters$ - ihu}!A2]*07}|,lc\n" +
    "16:28:44.406 [scala-execution-context-global-123] INFO  HelperUtils.Parameters$ - CC]>~R#,^#0JWyESarZdETDcvk)Yk'I?"
  val search = LogSearch


  it should "Correctly find the timestamp at the middle of a given file (technically string)" in {
    val actual = search.findMidpointTimestamp(testLogFile, 0, testLogFile.length)
    actual._1 shouldBe "16:28:44.352"
    actual._2 shouldBe 408
    actual._3 shouldBe 535
  }

  it should "Correctly check if a timestamp is within the search bounds" in {
    val time1 = "10:20:44.276"
    val time2 = "10:30:44.276"
    val time3 = "10:40:44.276"
    search.isWithinBounds(time1, time3, time2) shouldBe 0
    search.isWithinBounds(time1, time2, time3) shouldBe 1
    search.isWithinBounds(time2, time3, time1) shouldBe -1
    search.isWithinBounds(time1, time1, time1) shouldBe 0
    search.isWithinBounds("16:28:44.305", "16:28:44.406", "17:28:0:0")
  }

  it should "Correctly run binary search for exact timestamps" in {
    val existTarget1 = "16:28:44.276"
    val existTarget2 = "16:28:44.406"
    val doesNotExistTarget = "16:30:44.276"
    val duration = "0:0:0:0"

    search.indexOfLog(testLogFile, existTarget1, duration) shouldBe 0
    search.indexOfLog(testLogFile, existTarget2, duration) shouldBe 763
    search.indexOfLog(testLogFile, doesNotExistTarget, duration) shouldBe -1
  }

  it should "Correctly run binary search for timestamps within duration" in {
    val time1 = "16:28:44.200"
    val duration1 = "0:0:0:100"
    val time2 = "16:28:00.00"
    val duration2 = "1:0:0.0"
    val time3 = "16:28:44.300"
    val duration3 = "0:0:0:4"

    search.indexOfLog(testLogFile, time1, duration1) shouldBe 0
    search.indexOfLog(testLogFile, time2, duration2) shouldBe 408
    search.indexOfLog(testLogFile, time3, duration3) shouldBe -1
  }
}
