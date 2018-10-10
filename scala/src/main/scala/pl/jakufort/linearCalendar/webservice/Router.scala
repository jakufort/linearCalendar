package pl.jakufort.linearCalendar.webservice

import akka.http.scaladsl.model.{ContentType, HttpEntity, MediaTypes}
import akka.http.scaladsl.server.{Directives, Route}

object Router extends Directives with JSONSupport {
  val routes: Route = {
    pathPrefix("api") {
      pathPrefix("calendar") {
        path("generate") {
          post {
            entity(as[ServiceProperties]) { config =>
              complete(HttpEntity.Strict(ContentType(MediaTypes.`application/pdf`), PDFGeneratorService.generate(config)))
            }
          }
        }
      }
    }
  }
}
