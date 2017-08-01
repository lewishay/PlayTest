package models

import play.api.data.Form
import play.api.data.Forms._

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.io.StdIn._

/**
  * Created by Administrator on 26/06/2017.
  */
object Hangman {
  val hangmanImage = Array("         _______","         |     |","         |     O","         |    /|\\",
    "         |    / \\","         |","       __|___","       |    |")
  val wordList =
    Source.fromFile("C:/Users/Administrator/IdeaProjects/TestPlay/app/models/wordlist.txt").getLines().toArray
  val rand = new scala.util.Random
  var guessWord = ""
  var current = new ArrayBuffer[Char]
  var prevGuesses = new ArrayBuffer[Char]
  var savedGuessWord = ""
  var playing = false
  var counter = 0
  var failCounter = 0
  var result = "No guesses made yet. Please start the game."


  def newGame(): Unit = {
    guessWord = wordList(rand.nextInt(wordList.length))
    current = new ArrayBuffer[Char]
    for (z <- 1 to guessWord.length) current += '_'
    savedGuessWord = guessWord
    playing = true
    counter = 0
    failCounter = 0
    prevGuesses = new ArrayBuffer[Char]
  }

  def makeMove(guess: Char) = {
    if(playing) {
      var resultArray = new ArrayBuffer[String]
      resultArray += s"Turn number: ${counter + 1}. Remaining incorrect guesses: ${7 - failCounter}\n"
      if(prevGuesses.nonEmpty) resultArray += s"Previous guesses: ${prevGuesses.mkString(" ")}"
      resultArray += s"${current.mkString(" ")}"
      prevGuesses += guess
      if(guessWord.contains(guess)) {
        resultArray += "Correct guess."
        for(i <- 0 to guessWord.length - 1) {
          if(guessWord(i) == guess) current(i) = guess
        }
        guessWord = guessWord.replaceAll(guess.toString, " ")
        if(!current.mkString.contains("_")) {
          resultArray += "You win! Yes dude!"
          resultArray += s"The word was $savedGuessWord"
          playing = false
        }
        counter += 1
      }
      else {
        for (i <- 0 to failCounter) resultArray += s"${hangmanImage(i)}"
        failCounter += 1
        if (failCounter > 7) {
          resultArray += "Game over! YOU DIED!"
          resultArray += s"The word was $savedGuessWord"
          playing = false
        }
        counter += 1
      }
      result = resultArray.mkString("\n")
    }
    else {
      "You must start a new game first!"
    }
  }
}