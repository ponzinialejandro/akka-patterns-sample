package com.garba.viajes.aponzini.spray.endpoint

import akka.actor.{Actor, Props}
import spray.routing._
import spray.http._
import MediaTypes._
import com.garba.viajes.aponzini.common.{Person, WeatherActor}
import com.garba.viajes.aponzini.common.JsonConverter._
import com.garba.viajes.aponzini.requestProvideOrchestrator.{CityHistoryWeatherActor, WeatherHistoryRequest}
import spray.json._
import akka.pattern.ask
import akka.util.Timeout
import spray.util.LoggingContext

import scala.concurrent.duration._
import spray.util.LoggingContext
import spray.http.StatusCodes._
import spray.routing._

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class WeatherServices extends WeatherActor with HttpService{

  implicit def actorRefFactory = context

  implicit val timeout : Timeout = 15 seconds

  /*implicit val routingSetting : RoutingSettings = RoutingSettings.default

  implicit def myExceptionHandler(implicit log: LoggingContext) =
    ExceptionHandler {
      case e: ArithmeticException =>
        requestUri { uri =>
          log.warning("Request to {} could not be handled normally", uri)
          complete(InternalServerError, "Bad numbers, bad result!!!")
        }
    }

*/

  val cityHistoryWeatherActor = context.actorOf(Props(new CityHistoryWeatherActor))
  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test


  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling

  val person =
  path("person") {
    get {
      respondWithMediaType(`application/json`) {
        complete {
          Person("Bob", "Type A", 2L).toJson.toString()
        }
      }
    }
  }

  val home = {
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
              Person("Bob", "Type A", 2L).toJson.prettyPrint
            }
          })
        }
      }
    }

  def receive = runRoute(home ~ person ~ cityWeather)
}
/*
// this trait defines our service behavior independently from the service actor
trait Services extends HttpService {
  import com.garba.viajes.aponzini.common.JsonConverter._
  import spray.json._
  val person =
    path("/service") {
      get {
        respondWithMediaType(`application/json`) {
          complete {
            Person("Bob", "Type A", 2L).toJson.toString()
          }
        }
      }
    }
/*
  val cityWeather =
    path("city-weather") {
      get {
        request => {
          val futureResult = cityHistoryWeatherActor ? WeatherHistoryRequest
          futureResult.onComplete(result => {
            request.complete {
              Person("Bob", "Type A", 2L).toJson.prettyPrint
            }
          })
        }
      }
    }
*/
  val home = {
    path("/") {
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
}*/