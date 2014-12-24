package actors.bank

import akka.actor.Actor
import akka.actor.ActorRef
import akka.event.LoggingReceive

/**
 * Expanded example originated from Coursera Reactive Programming
 * class - Roland Kuhn lecture.
 *
 * Run: akka.Main
 * Args: -Dakka.loglevel=DEBUG -Dakka.actor.debug.receive=on (with LoggingReceive)
 */
object TransferAgent {
  case class Transfer(from: ActorRef, to: ActorRef, amount: BigInt)
  case object Done
  case class Failure(msg: String)
}

class TransferAgent extends Actor {
  import TransferAgent._

  def receive: Receive = {
    case Transfer(from, to, amount) => {
      from ! BankAccount.Withdrawal(amount)
      //discardOld true is default, but just to be explicit - now new action
      context.become(awaitWithdrawal(to, amount, sender), discardOld = true)
    }
  }

  def awaitWithdrawal(to: ActorRef, amount: BigInt, transferClient: ActorRef): Receive = LoggingReceive {
    case BankAccount.Done => {
      to ! BankAccount.Deposit(amount)
      context.become(awaitDeposit(transferClient))
    }
    case BankAccount.Failure(msg) => {
      transferClient ! Failure(msg)
      context.stop(self)
    }
  }

  def awaitDeposit(transferClient: ActorRef): Receive = LoggingReceive {
    case BankAccount.Done => {
      transferClient ! Done
      context.stop(self)
    }
    case BankAccount.Failure(msg) => {
      transferClient ! Failure(msg)
      context.stop(self)
    }
  }
}
