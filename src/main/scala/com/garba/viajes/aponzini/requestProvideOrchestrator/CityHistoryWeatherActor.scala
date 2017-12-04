package com.garba.viajes.aponzini.requestProvideOrchestrator

import akka.actor.Props
import com.garba.viajes.aponzini.common.WeatherActor

case class WeatherHistoryRequest()

class CityHistoryWeatherActor extends WeatherActor{
  override def receive = {
    case req : WeatherHistoryRequest =>
      val orquestrator = context.actorOf(Props(new RequestOrchestrator(sender())))
      orquestrator ! req
  }
}
