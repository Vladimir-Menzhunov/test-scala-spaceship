package errorhandler

import play.api.http.HttpErrorHandler
import play.api.libs.json.JsResultException
import play.api.mvc._
import play.api.mvc.Results._

import scala.concurrent._
import javax.inject.Singleton;

@Singleton
class ErrorHandler extends HttpErrorHandler {

  def onClientError(request: RequestHeader, statusCode: Int, message: String) = {
    Future.successful(
      Status(statusCode)("A client error occurred: " + statusCode)
    )
  }

  def onServerError(request: RequestHeader, exception: Throwable) = {
    Future.successful(
      exception match {
        case e: JsResultException => InternalServerError("A server error occurred: " + 400)
      }
    )
  }
}