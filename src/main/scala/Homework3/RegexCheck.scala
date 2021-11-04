package Homework3

import org.slf4j.LoggerFactory

import java.util.regex.Pattern
import HelperUtils.ObtainConfigReference

object RegexCheck {
  val config = ObtainConfigReference("randomLogGenerator") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }
  val logger = LoggerFactory.getLogger(RegexCheck.getClass)

  def collectInjectLogs(file: String, startTime:String, endTime:String, index : Int) : String = {
    val start = findLowerBound(file, startTime, index)
    val end = findUpperBound(file, endTime, index)
    logger.info(s"Collectings logs between $start and $end")
    val logsContainingInjection = file.substring(start, end)
      .split("\n")
      .filter(log => containsInjectionPattern(log))
     if (logsContainingInjection.length == 0) { return "No logs found" }
     else {logsContainingInjection.reduce((a,b) => a + "\n" + b)}

  }

  // Check the log message for instance of the regex injection as designated in the compile
  def containsInjectionPattern(message: String): Boolean = {
    val patternRegex = config.getString("randomLogGenerator.Pattern")
    val pattern = Pattern.compile(patternRegex)
    logger.info(s"Checking for $patternRegex in message: ${message}")
    pattern.matcher(message).find()
  }
  def findLowerBound(file: String, lowerBound: String, index: Int) : Int = {
    val newBound = file.lastIndexOf("\n", index) + 1
    val log = file.substring(newBound, newBound + 12)
    if (TimestampUtil.compareTimestamps(lowerBound, log)) {
      if (newBound <= 0) {return 0}
      else { return findLowerBound(file, lowerBound, newBound - 2) }
    }
    else { return index }
  }
  def findUpperBound(file: String, upperBound: String, index: Int) : Int = {
    val newBound = file.indexOf("\n", index) + 1
    val log = file.substring(newBound, newBound + 12)
    if (TimestampUtil.compareTimestamps(log, upperBound)) {
      if (newBound <=0) {return index}
      else { return findUpperBound(file, upperBound, newBound) }
    }
    else { return index }
  }

}
