package controllers

import javax.inject.Inject
import javax.inject.Singleton

import play.api.libs.json.JsError
import play.api.libs.json.JsSuccess
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import play.api.mvc.AbstractController
import play.api.mvc.ControllerComponents
import repositories.CardRepository

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

@Singleton
class CardController @Inject()(
  cc: ControllerComponents,
  cardRepository: CardRepository)(implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  import CardController._

  // TODO: add some form of authentication here....
  def get(id: Long) = Action.async { implicit request =>
    cardRepository.get(id).map { cardOption =>
      cardOption.map { card =>
        Ok(Json.toJson(card))
      }.getOrElse {
        NotFound("Card not found")
      }
    }
  }


  def create = Action.async { implicit request =>
    // TODO: handle this error better.
    val body = request.body.asJson.get

    CardCreate.format.reads(body) match {
      case JsSuccess(cardCreate, _) =>
        cardRepository.insert(
          cardCreate.deckId,
          cardCreate.japanese,
          cardCreate.furigana,
          cardCreate.english,
          cardCreate.notes)
          .map(card => Ok(Json.toJson(card)))
      case JsError(errors) =>
        Future.successful(BadRequest(s"Please give valid JSON. $errors"))
    }
  }

}

object CardController {
  case class CardCreate(deckId: Long, japanese: String, furigana: String, english: Seq[String], notes: String)

  object CardCreate {
    implicit val format: OFormat[CardCreate] = Json.format[CardCreate]
  }
}
