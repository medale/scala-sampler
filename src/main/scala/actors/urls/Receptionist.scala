package actors.urls

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props

/**
 * Example originated from Coursera Reactive Programming
 * class - Roland Kuhn lecture.
 */
object Receptionist {
  case class Get(url: String)
  case class Failed(url: String)
  case class Job(client: ActorRef, url: String)
  case class Result(url: String, links: Set[String])
}

class Receptionist extends Actor {

  import Receptionist._

  var reqNo = 0

  def receive = waiting

  val waiting: Receive = {
    //From class notes:
    //"Prefer context.become for different states, with data local to the behavior."
    case Get(url) => context.become(runNext(Vector(Job(sender, url))))
  }

  def running(queue: Vector[Job]): Receive = {
    case Controller.Result(links) =>
      val job = queue.head
      job.client ! Result(job.url, links)
      context.stop(sender)
      context.become(runNext(queue.tail))
    case Get(url) =>
      context.become(enqueueJob(queue, Job(sender, url)))
  }

  def runNext(queue: Vector[Job]): Receive = {
    reqNo += 1
    if (queue.isEmpty) waiting
    else {
      //actor names must be unique
      val controller = context.actorOf(Props[Controller], s"c$reqNo")
      controller ! Controller.Check(queue.head.url, 2)
      running(queue)
    }
  }

  def enqueueJob(queue: Vector[Job], job: Job): Receive = {
    if (queue.size > 3) {
      sender ! Failed(job.url)
      running(queue)
    } else running(queue :+ job)
  }
}
