package com.garba.viajes.aponzini.scatterGatter

import akka.actor.{ActorRef, Props}
import com.garba.viajes.aponzini.common.WeatherActor
import com.garba.viajes.aponzini.common.providers.{DarkSkyActor, WundergroundActor}

import scala.concurrent.duration._

case class ScatterGatterRequest()

class ScatterGatterOrquestrator(originalSender : ActorRef) extends WeatherActor {


  val darkActor = context.actorOf(Props(new DarkSkyActor()))
  val wundergroundActor = context.actorOf(Props(new WundergroundActor()))

  override def receive = {
    case ScatterGatterRequest => {
      val aggregatorActor = context.actorOf(Props(new WeatherAggregator(self, 4 seconds)))
      val services: List[ActorRef] = List(darkActor, wundergroundActor)
      val scatterActor = context.actorOf(Props(new WeatherScatter(services)))
      scatterActor.tell(None, aggregatorActor)
    }
    case aggregation : AggregationResult => {
      println("AGGREGATED")
      originalSender ! aggregation
    }
  }
}