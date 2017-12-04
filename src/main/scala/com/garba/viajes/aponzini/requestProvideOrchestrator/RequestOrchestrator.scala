package com.garba.viajes.aponzini.requestProvideOrchestrator

import akka.actor.{ActorRef, Props}
import com.garba.viajes.aponzini.common.WeatherActor
import com.garba.viajes.aponzini.common.providers.{OpenWeaterMapRequest, OpenWeatherMapActor, OpenWeatherMapMessage}

case class WeaterRequest()

class RequestOrchestrator(originalSender : ActorRef) extends WeatherActor{

  lazy val weatherActor : ActorRef = context.actorOf(Props(new OpenWeatherMapActor))
  lazy val historyActor : ActorRef = context.actorOf(Props(new CityHistoryActor))

  override def receive = {

    case WeaterRequest => {
      println("RequestOrchestrator.receive(WeaterRequest)")
      println("Call OpenWeatherMapActor")
      weatherActor ! OpenWeaterMapRequest
    }
    case message : OpenWeatherMapMessage => {
      println("RequestOrchestrator.receive(OpenWeatherMapMessage) from OpenWeatherMapActor")
      println("Call CityHistoryActor")
      historyActor ! message
    }
    case result : OpenWeaterMapsWithCityHistory => {
      println("RequestOrchestrator.receive(OpenWeaterMapsWithCityHistory) from CityHistoryActor")
      println("Return to originalSender OpenWeaterMapsWithCityHistory")
      originalSender ! result
      die
    }
  }
}
