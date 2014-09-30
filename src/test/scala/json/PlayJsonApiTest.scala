package json

import org.scalatest.FunSuite
import scala.io.Source
import scala.util.control.NonFatal

class PlayJsonApiTest extends FunSuite {

  var jsonOpt: Option[String] = None

  var jsonSourceOpt: Option[Source] = None
  try {
    jsonSourceOpt = Option(Source.fromFile("src/test/resources/configs/config1.json"))
    jsonOpt = jsonSourceOpt match {
      case Some(jsonSource) => Some(jsonSource.mkString)
      case None => None
    }
  } catch {
    case NonFatal(ex) => println(s"Non fatal exception! $ex")
  } finally {
    for (s <- jsonSourceOpt) {
      println("Closing source...")
      s.close
    }
  }

  test("A JSON String parsed should contain...") {
    for (json <- jsonOpt) {
      
    }
  }

  test("Sample Exception from scalatest page") {
    intercept[NoSuchElementException] {
      Set.empty.head
    }
  }

}