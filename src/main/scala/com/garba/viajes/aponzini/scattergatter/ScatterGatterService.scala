package com.garba.viajes.aponzini.scattergatter

import akka.actor.{ActorRef, Props}
import com.garba.viajes.aponzini.common.WeatherActor

import scala.concurrent.duration._

case class ScatterGatterRequest()

class ScatterGatterService(originalSender : ActorRef) extends WeatherActor {

  override def receive = {
    case ScatterGatterRequest => {
      val aggregatorActor = context.actorOf(Props(new WeatherAggregator(self, 4 seconds)))
      val darkActor = context.actorOf(Props(new DarkSkyActor(aggregatorActor)))
      val wundergroundActor = context.actorOf(Props(new WundergroundActor(aggregatorActor)))
      val services: List[ActorRef] = List(darkActor, wundergroundActor)
      val scatterActor = context.actorOf(Props(new ScatterWeather(services)))

      scatterActor ! None
    }
    case aggregation : AggregationResult => {
      println("AGGREGATED")
      originalSender ! aggregation
    }
  }
}