package com.garba.viajes.aponzini.scattergatter

import akka.actor.{ActorRef, Props}
import com.garba.viajes.aponzini.common.{DarkSkyActor, WeatherActor, WundergroundActor}

import scala.concurrent.duration._

case class ScatterGatterRequest()

class ScatterGatterService(originalSender : ActorRef) extends WeatherActor {

  override def receive = {
    case ScatterGatterRequest => {
      val aggregatorActor = system.actorOf(Props(new WeatherAggregator(self, 4 seconds)))
      val darkActor = system.actorOf(Props(new DarkSkyActor(aggregatorActor)))
      val wundergroundActor = system.actorOf(Props(new WundergroundActor(aggregatorActor)))
      val services: List[ActorRef] = List(darkActor, wundergroundActor)
      val scatterActor = system.actorOf(Props(new ScatterWeather(services)))

      scatterActor ! None
    }
    case aggregation : AggregationResult => {
      println("AGGREGATED")
      originalSender ! aggregation
    }
  }
}