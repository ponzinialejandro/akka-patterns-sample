package com.garba.viajes.aponzini.requestProvideOrchestrator

import akka.actor.Props
import com.garba.viajes.aponzini.common.WeatherActor

case class WeatherHistoryRequest()

class CityHistoryWeatherRequester extends WeatherActor{
  override def receive = {
    case req @ WeatherHistoryRequest =>
      println("CityHistoryWeatherRequester => receive WeatherHistoryRequest ")
      val orquestrator = context.actorOf(Props(new RequestOrchestrator(sender()))/*.withDispatcher("city-weather-dispatcher")*/)
      orquestrator ! req
  }
}
