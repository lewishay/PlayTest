package helpers

import javax.inject.Inject
import akka.stream.Materializer
import play.api.Logger
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class HangmanFilter @Inject() (implicit val mat: Materializer, ec: ExecutionContext) extends Filter {
  def apply(nextFilter: RequestHeader => Future[Result])
           (requestHeader: RequestHeader): Future[Result] = {
    nextFilter(requestHeader).map { result =>

      if(requestHeader.uri == "/hangman") {
        Logger.warn("Warning, hangman is a very addictive game. You will be playing for hours!")
      }
      result.withHeaders()
    }
  }
}
