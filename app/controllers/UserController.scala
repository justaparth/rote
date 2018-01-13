package controllers

import javax.inject.Inject
import javax.inject.Singleton

import play.api.libs.json.JsError
import play.api.libs.json.JsSuccess
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import play.api.mvc.AbstractController
import play.api.mvc.ControllerComponents
import repositories.UserRepository

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

@Singleton
class UserController @Inject()(
    cc: ControllerComponents,
    userRepository: UserRepository)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  import UserController._

  // TODO: add some form of authentication here....
  def get(id: Long) = Action.async { implicit request =>
    userRepository.get(id).map { userOption =>
      userOption.map { user =>
        Ok(Json.toJson(user))
      }.getOrElse {
        NotFound("User not found")
      }
    }
  }


  def create = Action.async { implicit request =>
    // TODO: handle this error better.
    val body = request.body.asJson.get

    UserCreate.format.reads(body) match {
      case JsSuccess(userCreate, _) =>
        userRepository.insert(userCreate.firstName, userCreate.lastName, userCreate.email)
          .map(user => Ok(Json.toJson(user)))
      case JsError(errors) =>
        Future.successful(BadRequest(s"Please give valid JSON. $errors"))
    }
  }

}

object UserController {
  case class UserCreate(firstName: String, lastName: String, email: String)

  object UserCreate {
    implicit val format: OFormat[UserCreate] = Json.format[UserCreate]
  }
}
