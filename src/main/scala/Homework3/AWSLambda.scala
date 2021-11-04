package Homework3

import com.amazonaws.services.lambda.runtime.{Context, LambdaLogger, RequestHandler}
import com.typesafe.config.{Config, ConfigFactory}
import com.amazonaws.services.s3.{AmazonS3, AmazonS3Client, AmazonS3ClientBuilder}
import com.google.gson.{Gson, GsonBuilder}
import org.slf4j.{Logger, LoggerFactory}
import SearchRPC._

import java.{lang, util}
import java.util.Map
import collection.JavaConverters._
import java.util.regex.Pattern

class AWSLambda extends RequestHandler[util.Map[String, String], String] {
  val logger = LoggerFactory.getLogger(classOf[AWSLambda])
  override def handleRequest(event: util.Map[String,String], context: Context): String = {
    val gson = new GsonBuilder().setPrettyPrinting.create

    logger.info(s"Triggering search from ${event.toString}")

    val response = searchForLogs(event)
    // log execution details
    logger.info("ENVIRONMENT VARIABLES: " + gson.toJson(System.getenv))
    logger.info("CONTEXT: " + gson.toJson(context))
    // process event
    logger.info("EVENT: " + gson.toJson(event))
    logger.info("EVENT TYPE: " + event.getClass.toString)
    response
  }

}

def getFile(event: util.Map[String, String]): String = {
  val bucket = "cs441hw2"
  val key = "LogFileGenerator.2021-10-05.log"
  val awsClient = AmazonS3ClientBuilder.defaultClient()
  awsClient.getObjectAsString(bucket, key)
}

def searchForLogs(event: util.Map[String, String]): String = {
  // Get file from AWS S3
  val file = getFile(event)
  // Parse user defined target time
  val timeInfo = parseTime(event)
  // Search for index of log within target event
  val searchResult = LogSearch.indexOfLog(file, timeInfo._1, timeInfo._2)
  // If -1, time does not exist so end search
  if (searchResult < 0) {return "Desired time event does not exist within log file"}
  // Other wise collect and return all logs within time interval that contain the injected string
  val timeUpperBound = TimestampUtil.addDurationToTime(timeInfo._1, timeInfo._2)
  RegexCheck.collectInjectLogs(file, timeInfo._1,timeUpperBound, searchResult)
}

def parseTime(event : util.Map[String, String]) : (String, String) = {
  val targetTime = event.get("time")
  val h = event.get("deltaHour")
  val m = event.get("deltaMinute")
  val s = event.get("deltaSecond")
  val ms = event.get("deltaMillisecond")
  val duration = List(h,m,s,ms).reduce((a,b) => a + ":" + b)
  (targetTime, duration)
}

