package actors.urls

import akka.actor.Actor
import akka.actor.ActorRef
import akka.pattern.pipe
import java.util.concurrent.Executor
import scala.concurrent.ExecutionContext

/**
 * Example originated from Coursera Reactive Programming
 * class - Roland Kuhn lecture.
 */
object Cache {
  case class Get(url: String)
  case class Result(client: ActorRef, url: String, body: String)
}

class Cache extends Actor {

  import Cache._

  //need implicit execution context for WebClient get
  implicit val exec = context.dispatcher.asInstanceOf[Executor with ExecutionContext]

  var cache = Map.empty[String, String]

  def receive = {
    case Get(url) =>
      if (cache contains url) sender ! cache(url)
      else {
        //sender will change - copy to local val
        val client = sender
        //pipeTo pattern support provided by pattern.pipe import
        WebClient get url map (Result(client, url, _)) pipeTo self
      }
    case Result(client, url, body) =>
      cache += url -> body
      client ! body
  }
}
