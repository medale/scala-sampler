package actors

import akka.actor.Actor
import akka.actor.Props
// import aliasing to avoid Main conflict
import akka.{ Main => AkkaMain }

/**
 * Expanded example originated from Coursera Reactive Programming
 * class - Roland Kuhn lecture.
 */
object Main {

  /**
   * Or create Eclipse run configuration with main class:
   * akka.Main and args fully-qualified classname, -Dakka.loglevel=WARNING
   */
  def main(args: Array[String]): Unit = {
    AkkaMain.main(Array("actors.Main"))
    //get printed immediately - akka main on new thread
    println("Closing out Akka?")
    //akka main shuts down when supervisor (Main) stops itself
  }
}

class Main extends Actor {

  //create a new actor
  //ActorContex.afterOf Props, with a given name
  //See Props companion object apply with no args for following API notes
  //Scala API: Returns a Props that has default values except for "creator"
  //which will be a function  that creates an instance of the supplied type
  //using the default constructor.
  val counter = context.actorOf(Props[Counter], "counter")

  println(s"counter is of type ${counter.getClass}")

  //counter (an ActorRef) tell "incr" (implicit sender - self)
  counter ! "incr"
  counter ! "incr"
  counter ! "get"

  //Note these get run as part of intializing a Main object, i.e.
  //before Main Actor is all ready to send or receive messages
  //so if we call self stop we will shut everything down
  //self ! "stop"

  def receive: Receive = {
    case count: Int => {
      println(s"Count is of type ${count.getClass}")
      println(s"Count was $count")
      sender ! "stop"
    }
    case "stop" => { //stop this actor - akka now shuts down because supervisor is down
      println("Shutting down Main...")
      context.stop(self)
    }
    case default => println(s"Don't know how to deal with $default of type ${default.getClass()}")
  }
}
