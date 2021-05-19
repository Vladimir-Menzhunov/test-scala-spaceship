package models

import play.api.libs.json.Json

case class Spaceship(timeOfArrival: Int, handleTime: Int)

object Spaceship {
  implicit val format = Json.format[Spaceship]
}