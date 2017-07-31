package models

import play.api.data.Form
import play.api.data.Forms.{mapping, number, of}

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.io.StdIn._

/**
  * Created by Administrator on 26/06/2017.
  */
case class Hangman(var hangmanImage: Array[String], var prevGuesses: ArrayBuffer[Char], var current: ArrayBuffer[Char]) {
  hangmanImage = Array("         _______","         |     |","         |     O","         |    /|\\","         |    / \\","         |","       __|___","       |    |")
  val wordList = Source.fromFile("C:/Users/Administrator/IdeaProjects/TestPlay/app/models/wordlist.txt").getLines().toArray
  val rand = new scala.util.Random

  def playGame(): Unit = {
    var guessWord = wordList(rand.nextInt(wordList.length))
    current = new ArrayBuffer[Char]
    for(z <- 1 to guessWord.length) current += '_'
    val savedGuessWord = guessWord
    var playing = true
    var counter = 0
    var failCounter = 0
    prevGuesses = new ArrayBuffer[Char]
    while(playing) {
      println(s"${Console.BLACK}Turn number: ${counter + 1}. Remaining incorrect guesses: ${7 - failCounter}\nPlease guess a letter.")
      if(prevGuesses.nonEmpty) println(s"Previous guesses: ${prevGuesses.mkString(" ")}")
      println(s"${Console.BLUE}${current.mkString(" ")}")
      var guess = readChar()
      prevGuesses += guess
      if(guessWord.contains(guess)) {
        println(s"${Console.GREEN}Correct guess.")
        for(i <- 0 to guessWord.length - 1) {
          if(guessWord(i) == guess) current(i) = guess
        }
        guessWord = guessWord.replaceAll(guess.toString, " ")
        if(!current.mkString.contains("_")) {
          println(s"${Console.GREEN}You win! Yes dude!")
          println(s"${Console.BLACK}The word was $savedGuessWord")
          playing = false
        }
        counter += 1
      }
      else {
        for(i <- 0 to failCounter) println(s"${Console.RED}${hangmanImage(i)}")
        failCounter += 1
        if(failCounter > 7) {
          println(s"${Console.RED}Game over! YOU DIED!")
          println(s"${Console.BLACK}The word was $savedGuessWord")
          playing = false
        }
        counter += 1
      }
    }
  }
}

