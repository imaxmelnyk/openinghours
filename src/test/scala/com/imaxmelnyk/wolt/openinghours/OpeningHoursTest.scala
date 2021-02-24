package com.imaxmelnyk.wolt.openinghours

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.imaxmelnyk.wolt.openinghours.http.Routes
import com.imaxmelnyk.wolt.openinghours.OpeningHoursTest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.io.Source

class OpeningHoursTest extends AnyWordSpec with Matchers with ScalatestRouteTest {
  private val input: String = readResourceAsString("input.json")
  private val output: String = readResourceAsString("output.txt")

  "The opening hours http app" should {
    "return pretty printed opening hours" in {
      Post("/openinghours", HttpEntity(ContentTypes.`application/json`, input)) ~> Routes.defaultRoutes ~> check {
        responseAs[String] shouldEqual output
      }
    }
  }
}

object OpeningHoursTest {
  def readResourceAsString(resource: String): String = {
    Source.fromInputStream(getClass.getResourceAsStream(s"/$resource")).mkString
  }
}
