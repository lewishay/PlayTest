package controllers

import javax.inject.{Inject, Singleton}

import akka.stream.Materializer
import play.api.http.ContentTypes
import play.api.libs.Comet
import play.api.mvc._

@Singleton
class CometController @Inject() (materializer: Materializer) extends Controller with Ticker {
  def index() = Action {
    Ok(views.html.clock())
  }

  def clock() = Action {
    Ok.chunked(stringSource via Comet.string("parent.clockChanged")).as(ContentTypes.HTML)
  }
}