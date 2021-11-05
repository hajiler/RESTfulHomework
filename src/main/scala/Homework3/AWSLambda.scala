package Homework3

import HelperUtils.ObtainConfigReference
import com.amazonaws.services.lambda.runtime.{Context, LambdaLogger, RequestHandler}
import com.typesafe.config.{Config, ConfigFactory}
import com.amazonaws.services.s3.{AmazonS3, AmazonS3Client, AmazonS3ClientBuilder}
import org.slf4j.{Logger, LoggerFactory}
import SearchRPC.*
import com.google.gson.{Gson, GsonBuilder, JsonObject}

import java.{lang, util}
import java.util.Map
import collection.JavaConverters.*
import java.util.regex.Pattern

class AWSLambda extends RequestHandler[util.Map[String, Object], String] {
  val logger = LoggerFactory.getLogger(classOf[AWSLambda])
  val gson = new GsonBuilder().setPrettyPrinting.create
  override def handleRequest(event: util.Map[String,Object], context: Context): String = {

    logger.info(s"Triggering search from ${event.toString}")

      val parameters = event.get("queryStringParameters").toString

      val valuesAsJSON = gson.fromJson(parameters, classOf[JsonObject])

      val response = searchForLogs(valuesAsJSON)



    return event.get("queryStringParameters").toString + response
  }

}

def getFile(): String = {
  val config = ObtainConfigReference("awsLambda") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }
  val bucket = config.getString("awsLambda.Bucket")
  val key = config.getString("awsLambda.Key")
  val awsClient = AmazonS3ClientBuilder.defaultClient()
  awsClient.getObjectAsString(bucket, key)
}

def searchForLogs(event: JsonObject): String = {
  // Get file from AWS S3
  val file = getFile()
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

def parseTime(event: JsonObject) : (String, String) = {
  val timeAsString = event.get("time").toString
  val targetTime = timeAsString.substring(1, timeAsString.length - 1)
  val h = event.get("deltaHour").toString
  val m = event.get("deltaMinute").toString
  val s = event.get("deltaSecond").toString
  val ms = event.get("deltaMillisecond").toString
  val duration = List(h,m,s,ms).reduce((a,b) => a + ":" + b)
  (targetTime, duration)
}

