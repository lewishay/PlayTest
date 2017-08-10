package controllers

import javax.inject.Inject

import play.api.mvc._
import reactivemongo.play.json.collection.JSONCollection
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import reactivemongo.api.Cursor
import play.modules.reactivemongo.{ // ReactiveMongo Play2 plugin
  MongoController,
  ReactiveMongoApi,
  ReactiveMongoComponents
}
import reactivemongo.play.json._
import models._
import models.JsonFormats._

import scala.concurrent.Future

class Mongo @Inject() (val reactiveMongoApi: ReactiveMongoApi) extends Controller
  with MongoController with ReactiveMongoComponents {
  def collection: Future[JSONCollection] = database.map(_.collection[JSONCollection]("persons"))

  def create = Action.async {
    val user = User(29, "John", "Smith")

    // insert the user
    val futureResult = collection.flatMap(_.insert(user))

    // when the insert is performed, send a OK 200 result
    futureResult.map(_ => Ok("Added user " + user.firstName + " " + user.lastName))
  }

  def createFromJson = Action.async(parse.json) { request =>
    /*
     * request.body is a JsValue.
     * There is an implicit Writes that turns this JsValue as a JsObject,
     * so you can call insert() with this JsValue.
     * (insert() takes a JsObject as parameter, or anything that can be
     * turned into a JsObject using a Writes.)
     */
    request.body.validate[User].map { user =>
      // `user` is an instance of the case class `models.User`
      collection.flatMap(_.insert(user)).map { lastError =>
        Logger.debug(s"Successfully inserted with LastError: $lastError")
        Created
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def findByName(lastName: String) = Action.async {
      // let's do our query
      val cursor: Future[Cursor[User]] = collection.map {
        // find all people with name `name`
        _.find(Json.obj("lastName" -> lastName)).
          // sort them by creation date
          sort(Json.obj("created" -> -1)).
          // perform the query and get a cursor of JsObject
          cursor[User]
      }

      // gather all the JsObjects in a list
      val futureUsersList: Future[List[User]] = cursor.flatMap(_.collect[List]())

      // everything's ok! Let's reply with the array
      futureUsersList.map { persons =>
        Ok(persons.toString)
      }
  }
}
