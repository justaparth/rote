package controllers

import javax.inject._

import org.webjars.play.WebJarAssets
import play.api.mvc._
import play.twirl.api.Html
import repositories.CardRepository
import repositories.ReviewRepository
import repositories.UserRepository

import scala.concurrent.ExecutionContext

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(
    cc: ControllerComponents,
    userRepository: UserRepository,
    cardRepository: CardRepository,
    reviewRepository: ReviewRepository,
    webJarAssets: WebJarAssets)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index() = Action.async { implicit request: Request[AnyContent] =>
    for {
      users   <- userRepository.list()
      cards   <- cardRepository.list()
      reviews <- reviewRepository.list()
    } yield {
      System.out.println(users.map(_.toString))
      System.out.println(cards.map(_.toString))
      System.out.println(reviews.map(_.toString))
      //Ok(views.html.index())
      Ok(views.html.main("hihi")(Html.apply("<p>hi</p>")))
    }
  }

  def reactEntry() = Action { implicit request =>
    Ok(views.html.reactstart("Your app is ready"))
  }
}
