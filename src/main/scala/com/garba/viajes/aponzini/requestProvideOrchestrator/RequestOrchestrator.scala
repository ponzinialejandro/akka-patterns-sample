package com.garba.viajes.aponzini.requestProvideOrchestrator

import akka.actor.{ActorRef, Props}
import com.garba.viajes.aponzini.scattergatter.WeatherActor

case class WeaterRequest()

class RequestOrchestrator(originalSender : ActorRef) extends WeatherActor{

  lazy val weatherActor : ActorRef = context.actorOf(Props(new OpenWeatherMapActor))
  lazy val historyActor : ActorRef = context.actorOf(Props(new CityHistoryActor))

  override def receive = {

    case WeaterRequest => weatherActor ! OpenWeaterMapRequest
    case message : OpenWeatherMapMessage => historyActor ! message
    case result : OpenWeaterMapsWithCityHistory => originalSender ! result
  }
}
