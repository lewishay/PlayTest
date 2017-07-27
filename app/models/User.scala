package models

import play.api.data.Form
import play.api.data.Forms._

case class User(age: Int, firstName: String, lastName: String)

//case class LastName(name: String)

object JsonFormats {
  import play.api.libs.json.Json

  // Generates Writes and Reads for User thanks to Json Macros
  implicit val userFormat = Json.format[User]
}

//object User {
//  val lastNameForm = Form(
//    mapping(
//      "Name" -> nonEmptyText
//    ) (LastName.apply)(LastName.unapply)
//  )
//}