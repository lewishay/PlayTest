package models

import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._

case class DrinkNameOnly(name: String)

object DrinkNameOnly {
  val singleDrinkForm = Form(
    mapping(
      "Name" -> nonEmptyText
    ) (DrinkNameOnly.apply)(DrinkNameOnly.unapply)
  )
}
