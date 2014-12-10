package actors.bank

import akka.actor.Actor
import akka.actor.Props
import akka.event.LoggingReceive

/**
 * Expanded example originated from Coursera Reactive Programming
 * class - Roland Kuhn lecture.
 *
 * Run: akka.Main
 * Args: -Dakka.loglevel=DEBUG -Dakka.actor.debug.receive=on (with LoggingReceive)
 */
class TransferMain extends Actor {

  val from = context.actorOf(Props[BankAccount], "from")
  val to = context.actorOf(Props[BankAccount], "to")

  from ! BankAccount.Deposit(100)

  def receive: Actor.Receive = {
    case BankAccount.Done => transfer(50)
    //to invoke error case run with 150
  }

  def transfer(amount: BigInt): Unit = {
    val agent = context.actorOf(Props[TransferAgent], "agent")
    agent ! TransferAgent.Transfer(from, to, amount)
    context.become(LoggingReceive {
      case TransferAgent.Done => {
        println("Transfer done")
        context.stop(self)
      }
      case TransferAgent.Failure(msg) => {
        println(s"Unable to complete transfer due to $msg")
        context.stop(self)
      }
    })
  }
}
