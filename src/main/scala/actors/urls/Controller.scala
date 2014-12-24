package actors.urls

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorRef
import akka.actor.Props
import akka.actor.ReceiveTimeout
import akka.util.Timeout
import scala.concurrent.duration._

/**
 * Example originated from Coursera Reactive Programming
 * class - Roland Kuhn lecture.
 */
object Controller {
  case class Result(links: Set[String])
  case class Check(url: String, depth: Int)
}

class Controller extends Actor with ActorLogging {

  //Overall receive timeout - must add ReceiveTimeout case
  //context.setReceiveTimeout(10.seconds)

  import context.dispatcher
  import Controller._

  context.system.scheduler.scheduleOnce(10.seconds, self, Timeout)

  var cache = Set.empty[String]
  var children = Set.empty[ActorRef]

  def receive = {
    case Check(url, depth) =>
      log.debug("{} checking {}", depth, url)
      if (!cache(url) && depth > 0)
        children += context.actorOf(Props(new Getter(url, depth - 1)))
      cache += url
    case Getter.Done =>
      children -= sender
      if (children.isEmpty) context.parent ! Result(cache)
    case ReceiveTimeout => children foreach (_ ! Getter.Abort)
    case Timeout        => children foreach (_ ! Getter.Abort)
  }
}
