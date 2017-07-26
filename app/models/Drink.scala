package models

import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import java.awt.image.BufferedImage

import scala.collection.mutable.ArrayBuffer

case class Drink(name: String, manufacturer: String, price: Double, volume: Int)

object Drink {
  val createDrinkForm = Form(
    mapping(
      "Name" -> nonEmptyText,
      "Manufacturer" -> nonEmptyText,
      "Price" -> of[Double],
      "Volume" -> number(min = 1)
    ) (Drink.apply)(Drink.unapply)
  )

  var drinks = ArrayBuffer(
    Drink("Pepsi", "PepsiCo", 1.19, 500),
    Drink("Sprite", "The Coca-Cola Company", 0.69, 330),
    Drink("Monster Ultra", "Monster Beverage", 1.89, 500)
  )
}
