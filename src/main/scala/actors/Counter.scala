package actors

import akka.actor.Actor

/**
 * Expanded example originated from Coursera Reactive Programming
 * class - Roland Kuhn lecture.
 */
class Counter extends Actor {

  def counter(n: Int): Receive = {
    case "incr" => context.become(counter(n + 1))
    case "get" => {
      sender ! n
      sender ! "Random message..."
    }
    case "stop" => {
      println("Telling sender to stop...")
      sender ! "stop"
    }
  }

  def receive: Receive = counter(0)
}
