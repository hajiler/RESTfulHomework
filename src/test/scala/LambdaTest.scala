import Homework3.*
import SearchRPC.TimeRequest
import org.scalatest.PrivateMethodTester
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import collection.JavaConverters.*
import com.google.gson.{Gson, GsonBuilder, JsonObject}

class LambdaTest extends AnyFlatSpec with Matchers with PrivateMethodTester {
  behavior of "Lambda util functions"
  val gson = new GsonBuilder().setLenient().create()


  it should "Parse an event" in {

    val values = "{deltaHour=0,deltaMillisecond=100,deltaMinute=0,deltaSecond=0,time=\"10:32:54:555\"}"

    val event = gson.fromJson(values, classOf[JsonObject])

    val actual = parseTime(event)
    actual._1 shouldBe "10:32:54:555"
    actual._2 shouldBe "0:0:0:100"
  }
  it should "Create correct url" in {
    val request = TimeRequest("", "0","0", "1","0")
  }
}
