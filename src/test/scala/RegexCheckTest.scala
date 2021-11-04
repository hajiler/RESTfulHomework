import Homework3.*
import org.scalatest.PrivateMethodTester
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RegexCheckTest extends AnyFlatSpec with Matchers with PrivateMethodTester {
  behavior of "Regex Checking functions"
  val testLogFile =
    "16:28:44.276 [scala-execution-context-global-123] WARN  HelperUtils.Parameters$ - A><YFqpg+~\"E1T\n" +
    "16:28:44.305 [scala-execution-context-global-123] DEBUG HelperUtils.Parameters$ - JrQB;P0\"&+6;&Dk-\n" +
    "16:28:44.314 [scala-execution-context-global-123] INFO  HelperUtils.Parameters$ - OsI1`qAeU5H;\\+\n" +
    "16:28:44.331 [scala-execution-context-global-123] INFO  HelperUtils.Parameters$ - [h!Q9PEY>L(NpKLBO\"Gjo:=4kRXQ_tZ!\n" +
    "16:28:44.352 [scala-execution-context-global-123] INFO  HelperUtils.Parameters$ - B?y&C\"C5rsb:2037;f&|vM#x?z|Ny|&<44Z8B&rF1#&M\n" +
    "16:28:44.370 [scala-execution-context-global-123] WARN  HelperUtils.Parameters$ - x2oBSI0/\\%CdfV2%ChSsnZ7vJo=2qJqZ%.\"kbc!0ne`y&m\n" +
    "16:28:44.389 [scala-execution-context-global-123] ERROR HelperUtils.Parameters$ - ihu}!A2]*07}|,lc\n" +
    "16:28:44.406 [scala-execution-context-global-123] INFO  HelperUtils.Parameters$ - CC]>~R#,^#0JWyESarZdETDcvk)Yk'I?"
  val testLogFileWithInjections =
      "16:28:44.276 [scala-execution-context-global-123] WARN  HelperUtils.Parameters$ - A><YFqpg+~\"E1T\n" +
      "16:28:44.305 [scala-execution-context-global-123] DEBUG HelperUtils.Parameters$ - JrQB;P0\"&+6;&Dk-\n" +
      "16:28:44.314 [scala-execution-context-global-123] INFO  HelperUtils.Parameters$ - 8G;,3m_T`G#H]&Yh:Ei1%fp''5`O9hQ7kB7obf2P5r%<Bqz8fMm#{JWqMdoc_2N/|wf8]\n" +
      "16:28:44.331 [scala-execution-context-global-123] INFO  HelperUtils.Parameters$ - [h!Q9PEY>L(NpKLBO\"Gjo:=4kRXQ_tZ!\n" +
      "16:28:44.352 [scala-execution-context-global-123] INFO  HelperUtils.Parameters$ - eXWZs:-SB8S5lK7fK5mB5mce1V8gh6i(_s.sQM\n" +
      "16:28:44.370 [scala-execution-context-global-123] WARN  HelperUtils.Parameters$ - x2oBSI0/\\%CdfV2%ChSsnZ7vJo=2qJqZ%.\"kbc!0ne`y&m\n" +
      "16:28:44.389 [scala-execution-context-global-123] ERROR HelperUtils.Parameters$ - vj41:OF\"Y@,l5~(lzo_>$7agTe8]\\bf3F9hbe1H5tcf0bf0bf0I5tX6fY7iX8tS6vO7m#oItLtX(,$1Q\"~`Ouh'm8jb`I4*^+\n" +
      "16:28:44.406 [scala-execution-context-global-123] INFO  HelperUtils.Parameters$ - CC]>~R#,^#0JWyESarZdETDcvk)Yk'I?"

  it should "Correctly find the lowest index of logs within lower bound" in {
    val time1 = "16:28:44.200"
    val time2 = "16:28:44.352"
    RegexCheck.findLowerBound(file=testLogFile, lowerBound=time1, index= 800) shouldBe 0
    RegexCheck.findLowerBound(file=testLogFile, lowerBound=time2, index= 800) shouldBe 406
    RegexCheck.findLowerBound(file=testLogFile, lowerBound=time2, index= 406) shouldBe 406
  }

  it should "Correctly find the highest index of logs within upper bound" in {
    val time1 = "16:28:44.331"
    val time2 = "16:28:44.406"
    val time3 = "16:28:44.276"
    RegexCheck.findUpperBound(file=testLogFile, upperBound=time1, index= 0) shouldBe 293
    RegexCheck.findUpperBound(file=testLogFile, upperBound=time2, index= 406) shouldBe 763
    RegexCheck.findUpperBound(file=testLogFile, upperBound=time3, index= 0) shouldBe 0
  }
  it should "Correctly gather no logs from a file with no injections in target area" in {
    val time1 = "16:28:30.331"
    val time2 = "16:28:50.406"
    RegexCheck.collectInjectLogs(testLogFile, time1, time2, 408) shouldBe "No logs found"
  }
  it should "Correctly gather the desired logs from a file with injections in target area" in {
    val time1 = "16:28:30.331"
    val time2 = "16:28:50.406"
    val expected = "16:28:44.314 [scala-execution-context-global-123] INFO  HelperUtils.Parameters$ - 8G;,3m_T`G#H]&Yh:Ei1%fp''5`O9hQ7kB7obf2P5r%<Bqz8fMm#{JWqMdoc_2N/|wf8]\n" +
      "16:28:44.352 [scala-execution-context-global-123] INFO  HelperUtils.Parameters$ - eXWZs:-SB8S5lK7fK5mB5mce1V8gh6i(_s.sQM\n" +
      "16:28:44.389 [scala-execution-context-global-123] ERROR HelperUtils.Parameters$ - vj41:OF\"Y@,l5~(lzo_>$7agTe8]\\bf3F9hbe1H5tcf0bf0bf0I5tX6fY7iX8tS6vO7m#oItLtX(,$1Q\"~`Ouh'm8jb`I4*^+"
    val actual = RegexCheck.collectInjectLogs(testLogFileWithInjections, time1, time2, 408)
    actual.equals(expected) shouldBe true
  }
}
