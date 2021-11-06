package Homework3

import Homework3.gRPCServer.logger

import java.util.logging.Logger
import io.grpc.{Server, ServerBuilder}
import SearchRPC.*
import java.net.{URI, URL}

import scala.concurrent.{ExecutionContext, Future}

/**
 * [[https://github.com/grpc/grpc-java/blob/v0.15.0/examples/src/main/java/io/grpc/examples/helloworld/HelloWorldServer.java]]
 */
object gRPCServer {
  private val logger = Logger.getLogger(classOf[gRPCServer].getName)

  def main(args: Array[String]): Unit = {
    val server = new gRPCServer(ExecutionContext.global)
    server.start()
    server.blockUntilShutdown()
  }

  private val port = 50051
}

class gRPCServer(executionContext: ExecutionContext) { self =>
  private[this] var server: Server = null

  private def start(): Unit = {
    // Build server
    server = ServerBuilder
      .forPort(gRPCServer.port)
      // Add service for SearchRequestRPC from gRPC client
      .addService(SearchLogsGrpc.bindService(new SearchRequestImp, executionContext))
      .build
      .start
    gRPCServer.logger.info("Server started, listening on " + gRPCServer.port)
    sys.addShutdownHook {
      System.err.println("*** shutting down gRPC server since JVM is shutting down")
      self.stop()
      System.err.println("*** server shut down")
    }
  }

  private def stop(): Unit = {
    if (server != null) {
      server.shutdown()
    }
  }

  private def blockUntilShutdown(): Unit = {
    if (server != null) {
      server.awaitTermination()
    }
  }

  private class SearchRequestImp extends SearchLogsGrpc.SearchLogs {
    override def searchBetween(request: TimeRequest): Future[LogResponse] = {
      // Create HTTP url
      val url = createURL(request)
      logger.info(s"Sending request to $url")
      // Send HTTP GET request to AWS Lambda endpoint, then read and process input
      val responseJsonFromAWSAPIGateway = scala.io.Source.fromURL(url)
        .bufferedReader()
        .lines()
        .skip(1)
        .reduce((a,b) => s"$a\n$b")
        .get()
      logger.info(s"AWS Lambda responded: $responseJsonFromAWSAPIGateway")
      // Create protobuf result and send back to gRPC client
      val reply = LogResponse(responseJsonFromAWSAPIGateway)
      Future.successful(reply)
    }
  }

  def createURL(request: TimeRequest) : String = {
    val baseURL = "https://q4pott1fpl.execute-api.us-east-2.amazonaws.com/Homework3Stage/cs441_lambda"
    val time = s"time=${request.time}"
    val deltaH = s"deltaHour=${request.deltaH}"
    val deltaM = s"deltaMinute=${request.deltaM}"
    val deltaS = s"deltaSecond=${request.deltaS}"
    val deltaMs =s"deltaMillisecond=${request.deltaMs}"
    val url= s"$baseURL?$time&$deltaH&$deltaM&$deltaS&$deltaMs"
    url
  }

}