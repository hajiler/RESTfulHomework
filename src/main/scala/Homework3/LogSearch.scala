package Homework3

import org.slf4j.LoggerFactory
import java.lang.Math.log

object LogSearch {

  val logger = LoggerFactory.getLogger(LogSearch.getClass)
  def startSearch(file : String, targetTime: String, duration: String): String = {
    // Calculate initial index bounds of binary search
    val low = 0
    val high = file.length
    // Calculate the upper limit (inclusive) of the search
    val limitTime = addDurationToTime(targetTime, duration)
    // Start search
    logger.info(s"Starting binary search for timestamps between $targetTime and $limitTime")
    val result = binarySearch(file, low, high, targetTime, limitTime, 0)
    if (result) { return "Found"}
    else {return "Not Found"}
  }

  def binarySearch(file : String, low : Int, high : Int, targetTime: String, timeLimit: String, stackCalls : Int) : Boolean = {
    // In the event of a bug leading to an infinite recursive calls, end the binary search to avoid processing costs.
    val maxCalls = log(file.length)
    if (stackCalls > maxCalls) {
      logger.error(s"Binary search exceeded designated stack frame limit $stackCalls > $maxCalls")
      throw Error("Ending program due")
    }

    // Base case, there are no more logs within the search bounds.
    logger.info(s"Conducting binary search for $targetTime between $low and $high")
    if (high <= low || low >= high) {
      return false
    }

    // Find the timestamp and index at the center of the search bounds.
    val midpoint = findMidpointTimestamp(file, low, high)
    val midpointTimestamp = midpoint._1
    val midpointLogStart = midpoint._2
    val midpointLogEnd = midpoint._3

    // If the timestamp is within the target area, return successfully. Otherwise proceed with the binary search on the
    // appropiate half of the file.
    isWithinBounds(targetTime, timeLimit, actual = midpointTimestamp) match {
      case 0 => return true
      case 1 => return binarySearch(file, low, midpointLogStart, targetTime, timeLimit, stackCalls + 1)
      case -1 => {
        if (midpointLogEnd < low) { return false }
        return binarySearch(file, midpointLogEnd, high, targetTime, timeLimit, stackCalls + 1)
      }
    }
  }

  def findMidpointTimestamp(file : String, low : Int, high : Int): (String, Int, Int) = {
    // Calculate the string index at the center of the search bounds.
    val midpointIndex : Int = (high - low ) / 2 + low
    // Find the index of the nearest timestamp (beginniner of a log message)
    val logIndexStart = file.lastIndexOf("\n", midpointIndex) + 1
    val logIndexEnd = file.indexOf("\n", midpointIndex) + 1

    // Parse the timestamp as a string
    val midpointTimestamp = file.substring(logIndexStart, logIndexStart + 12)
    logger.info(s"Timestamp $midpointTimestamp found at $logIndexStart between $low and $high")
    (midpointTimestamp, logIndexStart, logIndexEnd)
  }

  def isWithinBounds(target: String, limit: String, actual: String): Int = {
    logger.info(s"Checking $target <= $actual <= $limit:")
    // If target <= actual <= limit, time is in duration
    if (compareTimestamps(target, actual) && compareTimestamps(actual, limit)) {
      logger.info(s"$actual is within bounds")
      return 0
    }
    // If limit <= actual, then search upper half
    else if (compareTimestamps(limit, actual)) {
      logger.info(s"$actual is greater than the bounds")
      return 1
    }
    // Otherwise actual < target, so search lower half
    else {
      logger.info(s"$actual is less than the bounds")
      return -1
    }
  }

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
