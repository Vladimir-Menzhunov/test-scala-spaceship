package models

import play.api.libs.json.Json

case class Box(numberOfPlaces: Int)
object Box {
  implicit val format = Json.format[Box]
}