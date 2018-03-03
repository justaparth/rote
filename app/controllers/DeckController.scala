package controllers

import javax.inject.Inject
import javax.inject.Singleton

import play.api.libs.json.JsError
import play.api.libs.json.JsSuccess
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import play.api.mvc.AbstractController
import play.api.mvc.ControllerComponents
import repositories.DeckRepository

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

@Singleton
class DeckController @Inject()(
    cc: ControllerComponents,
    deckRepository: DeckRepository)(implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  import DeckController._

  // TODO: add some form of authentication here....
  def get(id: Long) = Action.async { implicit request =>
    deckRepository.get(id).map { deckOption =>
      deckOption.map { deck =>
        Ok(Json.toJson(deck))
      }.getOrElse {
        NotFound("Deck not found")
      }
    }
  }

  def getByFinderName(finder: String) = Action.async { implicit request =>
    if (finder == "byUserId") {
      val userId = request.getQueryString("userId").get.toLong
      deckRepository.getByUserId(userId).map { decksSeq =>
        Ok(Json.toJson(decksSeq))
      }
    } else {
      Future.successful(NotFound(s"No finder found with name $finder"))
    }
  }


  def create = Action.async { implicit request =>
    // TODO: handle this error better.
    val body = request.body.asJson.get

    DeckCreate.format.reads(body) match {
      case JsSuccess(deckCreate, _) =>
        deckRepository.insert(deckCreate.userId, deckCreate.name)
          .map(deck => Ok(Json.toJson(deck)))
      case JsError(errors) =>
        Future.successful(BadRequest(s"Please give valid JSON. $errors"))
    }
  }

}

object DeckController {
  case class DeckCreate(name: String, userId: Long)

  object DeckCreate {
    implicit val format: OFormat[DeckCreate] = Json.format[DeckCreate]
  }
}
