package actors.urls

import akka.actor.Actor
import akka.actor.Props
import scala.concurrent.duration.DurationInt
import akka.actor.ReceiveTimeout

/**
 * Expanded example originated from Coursera Reactive Programming
 * class - Roland Kuhn lecture.
 *
 * Run: akka.Main
 * akka.Main Args: actors.urls.Main JVM Args: -Dakka.loglevel=DEBUG
 */
class Main extends Actor {
  val receptionist = context.actorOf(Props[Receptionist], "receptionist")

  import Receptionist._

  receptionist ! Get("http://akka.io")

  //.seconds imported via implicit conversion in DurationInt
  context.setReceiveTimeout(60.seconds)

  def receive = {
    case Result(url, set) =>
      println(set.toVector.sorted.mkString(s"Results for '$url':\n", "\n", "\n"))
    case Failed(url) => println(s"Failed to fetch ")
    case ReceiveTimeout =>
      context.stop(self)
  }

  override def postStop(): Unit = {
    WebClient.shutdown()
  }
}
