package models

import play.api.libs.json.Json

case class Response(response: Int)
object Response {
  implicit val format = Json.format[Response]
}