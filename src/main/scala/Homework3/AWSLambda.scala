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

    val response = searchLogs("cs441hw2","LogFileGenerator.2021-10-05.log")
    // log execution details
    logger.info("ENVIRONMENT VARIABLES: " + gson.toJson(System.getenv))
    logger.info("CONTEXT: " + gson.toJson(context))
    // process event
    logger.info("EVENT: " + gson.toJson(event))
    logger.info("EVENT TYPE: " + event.getClass.toString)
    response
  }

  def searchLogs(bucket: String, key:String): String = {
    val patternRegex = "([a-c][e-g][0-3]|[A-Z][5-9][f-w]){5,15}"
    val pattern = Pattern.compile(patternRegex)

    val awsClient = AmazonS3ClientBuilder.defaultClient()
    val logFileAsString = awsClient.getObjectAsString(bucket, key)
    
    "hello mother fucker"
  }



}

