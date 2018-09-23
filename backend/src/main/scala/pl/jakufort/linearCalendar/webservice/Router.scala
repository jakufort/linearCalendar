package pl.jakufort.linearCalendar.webservice

import akka.http.scaladsl.model.{ContentType, HttpEntity, MediaTypes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object Router {
  val routes: Route = {
    pathPrefix("api") {
      pathPrefix("calendar") {
        path("generate") {
          post {
            complete(HttpEntity.Strict(ContentType(MediaTypes.`application/pdf`), PDFGeneratorService.generate()))
          }
        }
      }
    }
  }
}
