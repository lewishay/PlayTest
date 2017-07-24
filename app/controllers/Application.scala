package controllers

import play.api._
import play.api.mvc._

class Application extends Controller {
  def index = Action {
    Ok(views.html.index())
  }

  def out = Action {
    Redirect("http://hmpg.net/", 302)
  }

  def number(numb: Int) = Action {
    Ok(views.html.numberPage(numb))
  }

  def notFound = Action {
    NotFound("Page not found")
  }

  def badRequest = Action {
    BadRequest("Oops! An error occured.")
  }

  def internalServ = Action {
    InternalServerError("Error with the internal server...what have you done?")
  }

  def customStatus = Action {
    Status(666)("You are attempting to unleash hell. Please don't do this.")
  }

  def result1 = Action {
    Ok("This is the first result.")
  }

  def result2 = Action {
    Ok("This is the second result.")
  }

  val coolPage = TODO
}