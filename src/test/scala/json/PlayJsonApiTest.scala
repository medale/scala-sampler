package json

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import scala.io.Source
import scala.util.control.NonFatal
import com.fasterxml.jackson.core.JsonParseException
import play.api.libs.json.{ Json, JsValue }
import org.junit.runner.RunWith

//Documentation at https://www.playframework.com/documentation/2.3.0/ScalaJson
@RunWith(classOf[JUnitRunner])
class PlayJsonApiTest extends FunSuite {

  val validJsonFile = "src/test/resources/configs/validConfig.json"
  val invalidJsonFile = "src/test/resources/configs/invalidConfig.json"

  test("JSON object appName field should be JsonNator") {
	val jsonOpt = getJsonStringOption(validJsonFile)
    for (jsonStr <- jsonOpt) {
      try {
        val json: JsValue = Json.parse(jsonStr)
        val appNameOpt = (json \ "appName").asOpt[String]
        assert(appNameOpt.get === "JsonNator")
      } catch {
        case e: JsonParseException => println("Unable to parse JSON due to " + e)
      }
    }
  }
  
  test("JSON object appVersion field should be double 1.0") {
    val jsonOpt = getJsonStringOption(validJsonFile)
    for (jsonStr <- jsonOpt) {
      val json: JsValue = Json.parse(jsonStr)
      val appVersion = (json \ "appVersion").asOpt[Double]
      assert(appVersion.get === 1.0)
    }
  }
  
  test("JSON object foos array field processing for bar,baz") {
    val jsonOpt = getJsonStringOption(validJsonFile)
    for (jsonStr <- jsonOpt) {
      val json: JsValue = Json.parse(jsonStr)
      val foos = (json \ "foos").asOpt[List[String]]
      assert(foos.get === List("bar","baz"))
    }
  }

  test("Attempt to parse invalid JSON should throw JsonParseException") {
    val jsonOpt = getJsonStringOption(invalidJsonFile)
    intercept[JsonParseException] {
      for (jsonStr <- jsonOpt) {
        Json.parse(jsonStr)
      }
    }
  }
  
  ignore("Don't run this test") {
    assert(1 === 1)
  }

  def getJsonStringOption(fileName: String): Option[String] = {

    var jsonSourceOpt: Option[Source] = None
    try {
      jsonSourceOpt = Option(Source.fromFile(fileName))
      jsonSourceOpt match {
        case Some(jsonSource) => Some(jsonSource.mkString)
        case None => None
      }
    } catch {
      case NonFatal(ex) => {
        println(s"Non fatal exception! $ex")
        None
      }
    } finally {
      for (s <- jsonSourceOpt) {
        s.close
      }
    }
  }

}