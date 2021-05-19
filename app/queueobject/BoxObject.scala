package queueobject

import parameters.Parameters
import models.{Box, Response, Spaceship}

import scala.collection.mutable
import scala.reflect.runtime.universe.Try

object BoxObject {
  case class SpaceshipBox(spaceship: Spaceship, timeService: Int)

  val queue = mutable.Queue[SpaceshipBox]()
  val queueAllShips = mutable.Queue[SpaceshipBox]()
  var numberOfPlaces: Int = 0
  var initializer = false
  var lastTimeOfService = 0
  var lastSpaceshipBox = SpaceshipBox(Spaceship(0, 0), 0)

  def setBox(box: Box): Boolean = {
    if (box.numberOfPlaces >= Parameters.lowerBoundN && box.numberOfPlaces <= Parameters.upperBoundN && !initializer) {
      initializer = true
      numberOfPlaces = box.numberOfPlaces
      initializer
    } else initializer
  }

  def addShip(spaceship: Spaceship): String = {
    val timeOfArrival = spaceship.timeOfArrival
    val handleTime = spaceship.handleTime
    if(timeOfArrival >= Parameters.lowerBoundX &&
      timeOfArrival <= Parameters.upperBoundX &&
      handleTime >= Parameters.lowerBoundY &&
      handleTime <= Parameters.upperBoundY) {
      if ((lastSpaceshipBox.spaceship.timeOfArrival <= timeOfArrival &&
        queue.size < numberOfPlaces - 1) ||
        lastTimeOfService <= timeOfArrival) {

        if (lastTimeOfService < spaceship.timeOfArrival) {
          lastTimeOfService = spaceship.timeOfArrival
        }
        lastSpaceshipBox = SpaceshipBox(spaceship, lastTimeOfService)
        queue.enqueue(lastSpaceshipBox)
        queueAllShips.enqueue(lastSpaceshipBox)

        if (lastTimeOfService == 0) {
          lastTimeOfService = spaceship.timeOfArrival + spaceship.handleTime
        } else lastTimeOfService += spaceship.handleTime

        "Starship added successfully"
      } else {
        queueAllShips.enqueue(SpaceshipBox(spaceship, -1))
        "There are no places in the hangar or arrival time is incorrect"
      }
    } else "Invalid restrictions"

  }

  def deleteShip(): Spaceship = {
      Thread.sleep(queue.head.spaceship.handleTime * 1000)
      queue.dequeue().spaceship
  }

  def nextShip(): Response = {
    if(queueAllShips.nonEmpty) {
      Response(queueAllShips.dequeue.timeService)
    } else null
  }
}
