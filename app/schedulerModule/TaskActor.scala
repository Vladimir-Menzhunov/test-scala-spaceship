package schedulerModule

import akka.actor.{Actor, Props}
import queueobject.BoxObject

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class TaskActor @Inject()()(implicit ec: ExecutionContext) extends Actor {
  var numberShip = 1
  var shipsServiced = false

  /**
   * This method receives messages for an actor. Right now we are passing a string nothing from
   * TaskScheduler to it. You can inject your services in this module and call their method inside
   * the case block. And you have to inject the same in Task Scheduler and pass their reference
   * while creating the actor.*/
  override def receive: Receive = {
    case _ => {
      if(shipsServiced) {

          shipsServiced = false
      }
      if(BoxObject.queue.nonEmpty) {
        println(s"Servicing ship number $numberShip in progress")
        val ship = BoxObject.deleteShip()
        numberShip = numberShip + 1
        println(s"The plane arrived at ${ship.timeOfArrival} successfully served")
        if (BoxObject.queue.isEmpty) {
          println("All aircraft serviced!")
        }
      }
    }
  }
}

object TaskActor{
  def props: Props = Props[TaskActor]
}



