package com.garba.viajes.aponzini

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import scala.concurrent.duration._

case class ScatterGatterRequest()

class ScatterGatterService extends Actor {


  override def receive = {
    case ScatterGatterRequest => {
      implicit val system = ActorSystem("my-system")

      val aggregatorActor = system.actorOf(Props(new WeatherAggregator(self, 6 seconds)))
      val darkActor = system.actorOf(Props(new DarkSkyActor(aggregatorActor)))
      val wundergroundActor = system.actorOf(Props(new WundergroundActor(aggregatorActor)))
      val services: List[ActorRef] = List(darkActor, wundergroundActor)
      val scatterActor = system.actorOf(Props(new ScatterWeather(services)))

      scatterActor ! None
    }
    case AggregationResult(mapa) => {
      println(mapa)
    }
  }
}