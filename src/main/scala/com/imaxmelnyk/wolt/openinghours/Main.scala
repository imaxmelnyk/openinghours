package com.imaxmelnyk.wolt.openinghours

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import com.imaxmelnyk.wolt.openinghours.http.Routes
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContext
import scala.io.StdIn

object Main extends App with LazyLogging {
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "wolt-openinghours-system")
  implicit val executionContext: ExecutionContext = system.executionContext

  val bindingFuture = Http().newServerAt("localhost", 8080).bind(Routes.defaultRoutes)

  logger.info(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
