package models

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.data.Forms.mapping

case class Guess(guess: Char)

object Guess {
  val makeGuessForm = Form(
    mapping(
      "Guess" -> of[Char]
    )(Guess.apply)(Guess.unapply)
  )
}