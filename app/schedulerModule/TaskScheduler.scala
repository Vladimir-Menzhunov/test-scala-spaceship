package schedulerModule

import akka.actor.{ActorRef, ActorSystem, Props}

import java.text.SimpleDateFormat
import java.util.{Date, TimeZone}
import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext
import scala.concurrent.duration.{DurationInt, FiniteDuration, MILLISECONDS}

@Singleton
class TaskScheduler @Inject()(val system: ActorSystem)(implicit ec: ExecutionContext) {
  val taskActor: ActorRef = system.actorOf(Props(new TaskActor()), "ActorTask")

  def initActor(): String = {
    system.scheduler.scheduleWithFixedDelay(0.microseconds, 1.seconds, taskActor,"Nothing")
    "Robot Activated"
  }

}

