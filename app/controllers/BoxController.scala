package controllers

import models.{Box, Spaceship}

import play.api.libs.json.JsValue
import play.api.libs.json.Json.{stringify, toJson}
import play.api.mvc.{Action, AnyContent, InjectedController, ResponseHeader, Result}
import queueobject.BoxObject
import schedulerModule.TaskScheduler


import javax.inject.Inject

class BoxController @Inject()(taskScheduler: TaskScheduler) extends InjectedController {

  def setNumberOfPlaces(): Action[JsValue] = Action(parse.json) { implicit request =>
    val numberOfPlaces = request.body.as[Box]
    val statusInit = BoxObject.setBox(numberOfPlaces)
    if(statusInit) {
      val statusRobot = taskScheduler.initActor()
      //Ok(s"You have initialized the hangar - $statusRobot")
      Status(200)(s"You have initialized the hangar - $statusRobot")
    } else Status(200)(s"The number of seats is incorrect or the hangar has already been created")//Ok("The number of seats is incorrect or the hangar has already been created")

  }

  def getNumberOfPlaces: Action[AnyContent] = Action {
    val numberOfPlaces = BoxObject.numberOfPlaces
    Ok(s"Number of places in the hangar = $numberOfPlaces")
  }

  def addShip(): Action[JsValue] = Action(parse.json) { implicit request =>
    val ship = request.body.as[Spaceship]
    val statusAddShip = BoxObject.addShip(ship)
    //Ok(statusAddShip)
    Status(200)(statusAddShip)
  }

  def countShip: Action[AnyContent] = Action {
    val countShips = BoxObject.queue.size
    Ok(s"Number of ships = $countShips")
  }

  def nextShip(): Action[AnyContent] = Action {
    val timeNextShip = BoxObject.nextShip()
    //(StatusCodes.OK -> stringify(toJson(timeNextShip))).toString()
    if(BoxObject.initializer) {
      if (timeNextShip != null) {
      Ok(Status(OK).withHeaders("Body" -> stringify(toJson(timeNextShip))).toString())
      } else Ok(Status(OK).withHeaders("Body" -> "{}").toString())
    } else Ok("Hangar not initialized yet")
  }
}
