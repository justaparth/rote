package controllers

import javax.inject.Inject
import javax.inject.Singleton

import play.api.libs.json.JsError
import play.api.libs.json.JsSuccess
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import play.api.mvc.AbstractController
import play.api.mvc.ControllerComponents
import repositories.ReviewRepository

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

@Singleton
class ReviewController @Inject()(
  cc: ControllerComponents,
  reviewRepository: ReviewRepository)(implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  import ReviewController._

  // TODO: add some form of authentication here....
  def get(id: Long) = Action.async { implicit request =>
    reviewRepository.get(id).map { reviewOption =>
      reviewOption.map { review =>
        Ok(Json.toJson(review))
      }.getOrElse {
        NotFound("Review not found")
      }
    }
  }


  def create = Action.async { implicit request =>
    // TODO: handle this error better.
    val body = request.body.asJson.get

    ReviewCreate.format.reads(body) match {
      case JsSuccess(reviewCreate, _) =>
        reviewRepository.insert(
          reviewCreate.cardId,
          reviewCreate.userId,
          reviewCreate.englishResponse,
          reviewCreate.japaneseResponse,
          reviewCreate.englishCorrect,
          reviewCreate.japaneseCorrect
        ).map(review => Ok(Json.toJson(review)))
      case JsError(errors) =>
        Future.successful(BadRequest(s"Please give valid JSON. $errors"))
    }
  }

}

object ReviewController {

  case class ReviewCreate(
    cardId: Long,
    userId: Long,
    englishResponse: String,
    japaneseResponse: String,
    englishCorrect: Boolean,
    japaneseCorrect: Boolean)

  object ReviewCreate {
    implicit val format: OFormat[ReviewCreate] = Json.format[ReviewCreate]
  }

}
