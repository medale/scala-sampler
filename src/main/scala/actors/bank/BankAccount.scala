package actors.bank

import akka.actor.Actor
import akka.event.LoggingReceive

object BankAccount {
  case class Deposit(amount: BigInt) {
    require(amount > 0)
  }
  case class Withdrawal(amount: BigInt) {
    require(amount > 0)
  }
  case object Done
  case class Failure(msg: String)
}

class BankAccount extends Actor {

  import BankAccount._

  private var balance = BigInt(0)

  def receive: Actor.Receive = LoggingReceive {
    case Deposit(amount) => {
      balance += amount
      //access the actor's name via self.path
      println(s"Balance now $amount for ${self.path.name}")
      sender ! Done
    }
    case Withdrawal(amount) => {
      if (balance >= amount) {
        balance -= amount
        sender ! Done
      } else {
        sender ! Failure(s"Withdrawal amount $amount exceeds current balance $balance")
      }
    }
    case default => sender ! Failure(s"Don't know how to handle $default")
  }
}
