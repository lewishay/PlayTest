package controllers

import javax.inject.Inject
import javax.xml.transform.stream.StreamSource

import models.{Drink, DrinkNameOnly, Hangman}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import akka.stream.scaladsl._
import akka.util.ByteString
import play.api.http.HttpEntity
import play.api.libs.json.Json

class Application @Inject() (val messagesApi: MessagesApi) extends Controller with I18nSupport {
  def index = Action {
    Ok(views.html.index()).withSession("mySession" -> "Lewis")
  }

  def out = Action {
    Redirect("http://hmpg.net/", 302)
  }

  def badRequest = Action {
    BadRequest("Oops! An error occured.")
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

  def hello(name: String) = Action {
    Ok("Hello " + name + "!")
  }

  def helloSuperUser() = Action {
    Redirect(routes.Application.hello("SuperUser"))
  }

  def number(numb: Int) = Action {
    Ok("Your number is " + numb + "!").discardingCookies(DiscardingCookie("SuperCookie"))
  }

  def listMonths(numMonths: Option[Int]) = Action {
    val months = List("January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
      "November", "December")
    numMonths match {
      case Some(x) => if (x > 12) {
        Ok("Error, invalid number")
      }
      else {
        Ok("The months of the year are " + months.slice(0, x))
      }
      case None => Ok("The months of the year are " + months)
    }
  }

  def getSessionName = Action {
    request =>
      request.session.get("mySession").map {
        user => Ok("The user of this session is: " + user)
      }.getOrElse {
        Unauthorized("Oops, there is no saved session.")
      }
  }

  def removeSession = Action {
    Ok("Session removed.").withNewSession
  }

  def getDrinks = Action { implicit request =>
    Ok(views.html.listDrinks(Drink.drinks, Drink.createDrinkForm))
  }

  def createDrink = Action { implicit request =>
    val formValidationResult = Drink.createDrinkForm.bindFromRequest
    formValidationResult.fold({ formWithErrors =>
      BadRequest(views.html.listDrinks(Drink.drinks, formWithErrors))
    }, { drink =>
      Drink.drinks.append(drink)
      Redirect(routes.Application.getDrinks)
    })
  }

  def updateDrinks = Action { implicit request =>
    Ok(views.html.updateDrinks(Drink.createDrinkForm))
  }

  def updateDrink = Action { implicit request =>
    val formValidationResult = Drink.createDrinkForm.bindFromRequest
    formValidationResult.fold({ formWithErrors =>
      BadRequest(views.html.updateDrinks(formWithErrors))
    }, { drink =>
      if(Drink.drinks.map(x => x.name).contains(drink.name)) {
        Drink.drinks = Drink.drinks.filter(x => x.name != drink.name)
        Drink.drinks.append(drink)
        Redirect(routes.Application.updateDrinks)
      }
      else {
        BadRequest(views.html.updateDrinks(Drink.createDrinkForm, "No drink with that name exists!"))
      }
    })
  }

  def deleteDrinks = Action { implicit request =>
    Ok(views.html.deleteDrinks(DrinkNameOnly.singleDrinkForm))
  }

  def deleteDrink = Action { implicit request =>
    val formValidationResult = DrinkNameOnly.singleDrinkForm.bindFromRequest
    formValidationResult.fold({ formWithErrors =>
      BadRequest(views.html.deleteDrinks(formWithErrors))
    }, { drink =>
      if(Drink.drinks.map(x => x.name).contains(drink.name)) {
        Drink.drinks = Drink.drinks.filter(x => x.name != drink.name)
        Redirect(routes.Application.deleteDrinks)
      }
      else {
        BadRequest(views.html.deleteDrinks(DrinkNameOnly.singleDrinkForm, "No drink with that name exists!"))
      }
    })
  }

  def database = Action {
    Ok(views.html.database())
  }

  def hangman = Action {
    Ok(views.html.hangman(new Hangman(null, null, null)))
  }

  def file1 = Action {
    Ok.sendFile(
      content = new java.io.File("public/IntelliJ Cheat Sheet.pdf"),
      fileName = _ => "cheatSheet.pdf")
  }

  def file2 = Action {
    val file = new java.io.File("public/annual_report_2009.pdf")
    val path: java.nio.file.Path = file.toPath
    val source: Source[ByteString, _] = FileIO.fromPath(path)
    val contentLength = Some(file.length())
    Result(
      header = ResponseHeader(200, Map.empty),
      body = HttpEntity.Streamed(source, contentLength, Some("application/pdf"))
    )
  }

  def chunked1 = Action {
    val stringList = Source.apply(List("Bazooka", "Mortar", "Banana bomb", "Holy hand grenade", "Homing pigeon"))
    Ok.chunked(stringList)
  }

  def chunked2 = Action {
    val jsonList = Source.apply(List(scala.io.Source.fromURL("http://json-schema.org/example/geo.json").mkString))
    Ok.chunked(jsonList)
  }
}