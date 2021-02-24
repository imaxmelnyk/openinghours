package com.imaxmelnyk.wolt.openinghours.http

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import com.imaxmelnyk.wolt.openinghours.exceptions.InvalidFormatException
import com.imaxmelnyk.wolt.openinghours.models.OpeningHours
import com.typesafe.scalalogging.LazyLogging
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport

object Routes extends ErrorAccumulatingCirceSupport with LazyLogging {
  lazy val defaultRoutes: Route = {
    (post & path("openinghours") & entity(as[OpeningHours.Schedule])) { schedule =>
      try {
        val prettyOpeningHours: String = OpeningHours(schedule).toString

        logger.info(s"\n$prettyOpeningHours")
        complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, prettyOpeningHours))
      } catch {
        case InvalidFormatException =>
          logger.error(InvalidFormatException.getLocalizedMessage)
          complete(StatusCodes.BadRequest, InvalidFormatException.getLocalizedMessage)
        case e: Exception =>
          logger.error(e.getLocalizedMessage)
          complete(StatusCodes.InternalServerError, e.getLocalizedMessage)
      }
    }
  }
}
