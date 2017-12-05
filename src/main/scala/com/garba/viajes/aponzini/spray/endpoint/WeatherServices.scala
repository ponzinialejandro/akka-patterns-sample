package com.garba.viajes.aponzini.spray.endpoint

import akka.actor.Props
import spray.http._
import MediaTypes._
import com.garba.viajes.aponzini.common.{Person, WeatherActor}
import com.garba.viajes.aponzini.common.JsonConverter._
import com.garba.viajes.aponzini.requestProvideOrchestrator.{CityHistoryWeatherRequester, OpenWeaterMapsWithCityHistory, WeatherHistoryRequest}
import spray.json._
import akka.pattern.ask
import akka.util.Timeout
import com.garba.viajes.aponzini.ScatterGatherFirstCompletedRouter.{FirstCompleteRequest, FirstCompleteRequester}

import scala.concurrent.duration._
import spray.routing._

class WeatherServices extends WeatherActor with HttpService{

  implicit def actorRefFactory = context

  implicit val timeout : Timeout = 15 seconds

  val cityHistoryWeatherActor = context.actorOf(Props(new CityHistoryWeatherRequester))
  val firstCompleteActor = context.actorOf(Props(new FirstCompleteRequester))

  val routes = {
    path("") {
      get {
        // respond with text/html.
        respondWithMediaType(`text/html`) {
          complete {
            // respond with a set of HTML elements
            <html>
              <body>
                <h1>HOME</h1>
              </body>
            </html>
          }
        }
      }
    }
  }

  val cityWeather =
    path("cityweather") {
      get {
        request => {
          val futureResult = cityHistoryWeatherActor ? WeatherHistoryRequest
          futureResult.onComplete(result => {
            request.complete {
              result.toString
            }
          })
        }
      }
    }

  val firstComplete =
    path("firstcomplete") {
      get {
        request => {
          val futureResult = firstCompleteActor ? FirstCompleteRequest
          val mapFuture = futureResult.mapTo[OpenWeaterMapsWithCityHistory]
          mapFuture.onComplete(result => {
            println(result)
          })
        }
      }
    }

  def receive = runRoute(routes ~ cityWeather ~ firstComplete)
}