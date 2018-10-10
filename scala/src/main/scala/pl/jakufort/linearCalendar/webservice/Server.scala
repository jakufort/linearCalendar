package pl.jakufort.linearCalendar.webservice

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

object Server {

  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("my-system")
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    Http().bindAndHandle(Router.routes, "localhost", 8080)

    println(s"Server online at http://localhost:8080/")
  }

}
