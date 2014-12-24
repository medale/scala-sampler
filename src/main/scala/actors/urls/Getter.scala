package actors.urls

import akka.actor.Actor
import akka.actor.Status
import akka.pattern.pipe
import java.util.concurrent.Executor
import scala.concurrent.ExecutionContext
import scala.util.Success
import scala.util.Failure

/**
 * Example originated from Coursera Reactive Programming
 * class - Roland Kuhn lecture.
 */
object Getter {
  case class Done()
  case class Abort()

  val A_TAG = "(?i)<a ([^>]+)>.+?</a>".r
  val HREF_ATTR = """\s*(?i)href\s*=\s*(?:"([^"]*)"|'([^']*)'|([^'">\s]+))""".r

  def findLinks(body: String): Iterator[String] = {
    for {
      anchor <- A_TAG.findAllMatchIn(body)
      HREF_ATTR(dquot, quot, bare) <- anchor.subgroups
    } yield if (dquot != null) dquot
    else if (quot != null) quot
    else bare
  }
}

class Getter(url: String, depth: Int) extends Actor {

  import Controller._
  import Getter._

  implicit val exec = context.dispatcher.asInstanceOf[Executor with ExecutionContext]

  // Common interaction pattern - get future with onComplete Success/Failure
  //  val future = WebClient.get(url)
  //  future onComplete {
  //    case Success(body) => self ! body
  //    case Failure(err)  => self ! Status.Failure(err)
  //  }

  //from akka.pattern.pipe - replace commented-out code above
  WebClient get url pipeTo self

  def receive = {
    case body: String =>
      for (link <- findLinks(body))
        context.parent ! Controller.Check(link, depth)
      stop()
    case _: Status.Failure => stop()
    case Abort             => stop()
  }

  def stop(): Unit = {
    context.parent ! Done
    context.stop(self)
  }
}
