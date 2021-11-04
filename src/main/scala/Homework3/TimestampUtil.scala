package Homework3

object TimestampUtil {
  // Return true if t1 <= t2, else false
  def compareTimestamps(t1: String, t2: String) : Boolean = {
    t1.split("\\W")
      .zip(t2.split("\\W"))
      .map((a,b) => (a.toInt, b.toInt))
      .foreach((a,b) => {
        if (a != b) {
          return a < b
        }
      })
    return true
  }

  // Add a duration in the format h:m:s:ms to a timestamp in format h:m:s.ms
  def addDurationToTime(t1 : String, d: String): String = {
    t1.split("\\W")
      .zip(d.split("\\W"))
      .map((a,b) => a.toInt + b.toInt)
      .map(a => a.toString)
      .reduce((a,b) => a + ":" + b)
  }
}
