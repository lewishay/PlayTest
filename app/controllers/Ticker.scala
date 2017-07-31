package controllers

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import akka.stream.scaladsl.Source

import scala.concurrent.duration._

trait Ticker {

  def stringSource: Source[String, _] = {
    val df: DateTimeFormatter = DateTimeFormatter.ofPattern("HH mm ss")
    val tickSource = Source.tick(0 millis, 100 millis, "TICK")
    val s = tickSource.map((tick) => df.format(ZonedDateTime.now()))
    s
  }
}
