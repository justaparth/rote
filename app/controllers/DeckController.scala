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


  def create = Action.async { implicit request =>
    // TODO: handle this error better.
    val body = request.body.asJson.get

    DeckCreate.format.reads(body) match {
      case JsSuccess(deckCreate, _) =>
        deckRepository.insert(deckCreate.name)
          .map(deck => Ok(Json.toJson(deck)))
      case JsError(errors) =>
        Future.successful(BadRequest(s"Please give valid JSON. $errors"))
    }
  }

}

object DeckController {
  case class DeckCreate(name: String)

  object DeckCreate {
    implicit val format: OFormat[DeckCreate] = Json.format[DeckCreate]
  }
}
