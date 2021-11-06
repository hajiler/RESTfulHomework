package Homework3

import HelperUtils.ObtainConfigReference

import java.util.concurrent.TimeUnit
import java.util.logging.{Level, Logger}
import io.grpc.{ManagedChannel, ManagedChannelBuilder, StatusRuntimeException}

import scala.io.StdIn.readLine
import SearchRPC.*

object gRPCClient {
  val config = ObtainConfigReference("awsLambda") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }

  def apply(host: String, port: Int): gRPCClient = {
    val channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build
    val blockingStub = SearchLogsGrpc.blockingStub(channel)
    new gRPCClient(channel, blockingStub)
  }

  def main(args: Array[String]): Unit = {
    val host = config.getString("awsLambda.Host")
    val port = config.getInt("awsLambda.Port")
    val client = gRPCClient(host, port)
    try {
      val user = args.headOption.getOrElse("16.28.44.771")

      val time = config.getString("awsLambda.Time")
      val h = config.getString("awsLambda.deltaH")
      val m = config.getString("awsLambda.deltaM")
      val s = config.getString("awsLambda.deltaS")
      val ms = config.getString("awsLambda.deltaMs")
      client.makeSearchRequest(user, (h,m,s,ms))
    } finally {
      client.shutdown()
    }

  }
}

class gRPCClient private(private val channel: ManagedChannel,
                         private val blockingStub: SearchLogsGrpc.SearchLogsBlockingStub) {
  private[this] val logger = Logger.getLogger(classOf[gRPCClient].getName)

  def shutdown(): Unit = {
    channel.shutdown.awaitTermination(5, TimeUnit.SECONDS)
    logger.warning("Shutting down due to timeout")
  }

  def makeSearchRequest(time: String, delta: (String,String,String,String)): Unit = {
    logger.info("Constructing log search request to gRPC server")
    val delimiter = "\n--------------------------------------------------------------------------------------------------------------------------------------------------------\n"
    logger.info(s"Looking for logs containing injection regex starting at $time")
    val request = TimeRequest(time, delta._1,delta._2, delta._3,delta._4)
    try {
      logger.info("Sending query to gRPC server")
      val response = blockingStub.searchBetween(request)
      logger.info(s"Query results: $delimiter${response.log}$delimiter")
    }
    catch {
      case e: StatusRuntimeException =>
        logger.warning(s"RPC failed: ${e.getStatus}")
    }
  }
}