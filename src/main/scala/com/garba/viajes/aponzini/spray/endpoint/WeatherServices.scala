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
import com.garba.viajes.aponzini.common.providers.DarkSkyActor
import com.garba.viajes.aponzini.scatterGatterWithRouter.{ScatterGatterBroadcastRequester, WeatherRouterRequest}

import scala.concurrent.duration._
import spray.routing._

class WeatherServices extends WeatherActor with HttpService {

  implicit def actorRefFactory = context

  implicit val timeout: Timeout = 30 seconds

  val cityHistoryWeatherActor = context.actorOf(Props(new CityHistoryWeatherRequester))
  val firstCompleteActor = context.actorOf(Props(new FirstCompleteRequester))
  val scatterGatterActor = context.actorOf(Props(new ScatterGatterBroadcastRequester))
  val darkSkyProviderActor = context.actorOf(Props(new DarkSkyActor))

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
          futureResult.onComplete(result => {
            request.complete {
              result.toString
            }
          })
        }
      }
    }


  val scatterGatter =
    path("scattergatter") {
      get {
        request => {
          val futureResult = scatterGatterActor ? WeatherRouterRequest
          futureResult.onComplete(result => {
            request.complete {
              result.toString
            }
          })
        }
      }
    }

  val darkySky =
    path("darksky") {
      get {
        request => {
          val futureResult = darkSkyProviderActor ? None
          futureResult.onComplete(result => {
            request.complete {
              result.toString
            }
          })
        }
      }
    }


  def receive = runRoute(routes ~ cityWeather ~ firstComplete ~ scatterGatter ~ darkySky)
}