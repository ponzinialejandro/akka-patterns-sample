package com.garba.viajes.aponzini.spray.endpoint

import akka.actor.{ActorRef, Props}
import akka.http.scaladsl.server.{Directives, Route}
import akka.pattern.ask
import akka.util.Timeout
import com.garba.viajes.aponzini.ScatterGatherFirstCompletedRouter.{FirstCompleteRequest, FirstCompleteRequester}
import com.garba.viajes.aponzini.common.ActorSystemContext
import com.garba.viajes.aponzini.requestProvideOrchestrator.{CityHistoryWeatherRequester, WeatherHistoryRequest}
import com.garba.viajes.aponzini.scatterGatterWithRouter.{ScatterGatterBroadcastRequester, WeatherRouterRequest}
import com.garba.viajes.aponzini.singleRequest.DarkSkyRequester

import scala.concurrent.duration._
import scala.language.postfixOps
import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport._

class WeatherServices extends Directives with ActorSystemContext {

  //implicit def actorRefFactory = system

  implicit val timeout: Timeout = 30 seconds

  lazy val cityHistoryWeatherActor: ActorRef = system.actorOf(Props(new CityHistoryWeatherRequester))
  lazy val firstCompleteActor: ActorRef = system.actorOf(Props(new FirstCompleteRequester))
  lazy val scatterGatterActor: ActorRef = system.actorOf(Props(new ScatterGatterBroadcastRequester))
  lazy val darkSkyProviderActor: ActorRef = system.actorOf(Props(new DarkSkyRequester))

  val home: Route = {
    path("") {
      get {
        // respond with text/html.
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

  val cityWeather: Route =
    path("cityweather") {
      get {
        complete {
          (cityHistoryWeatherActor ? WeatherHistoryRequest).mapTo[String]
        }
      }
    }

  val firstComplete: Route =
    path("firstcomplete") {
      get {
        complete {
          (firstCompleteActor ? FirstCompleteRequest).mapTo[String]
        }
      }
    }


  val scatterGatter: Route =
    path("scattergatter") {
      get {
        complete {
          (scatterGatterActor ? WeatherRouterRequest).mapTo[String]
        }
      }
    }

  val darkySky: Route =
    path("darksky") {
      get {
        complete {
          (darkSkyProviderActor ? None).mapTo[String]
        }
      }
    }


  val routes: Route = encodeResponse {
    home ~ cityWeather ~ firstComplete ~ scatterGatter ~ darkySky
  }

}