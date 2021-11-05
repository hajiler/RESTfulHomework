package Homework3

import java.util.concurrent.TimeUnit
import java.util.logging.{Level, Logger}
import io.grpc.{ManagedChannel, ManagedChannelBuilder, StatusRuntimeException}
import SearchRPC._

object gRPCClient {
  def apply(host: String, port: Int): gRPCClient = {
    val channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build
    val blockingStub = SearchLogsGrpc.blockingStub(channel)
    new gRPCClient(channel, blockingStub)
  }

  def main(args: Array[String]): Unit = {
    val client = gRPCClient("localhost", 50051)
    try {
      val user = args.headOption.getOrElse("world")
      client.makeSearchRequest(user)
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
  }

  def makeSearchRequest(time: String): Unit = {
    logger.info(s"Looking for messages at $time")
    val request = TimeRequest(time, "0","0", "0","0")
    try {
      val response = blockingStub.searchBetween(request)
      logger.info("Log found: " + response.log)
    }
    catch {
      case e: StatusRuntimeException =>
        logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus)
    }
  }
}