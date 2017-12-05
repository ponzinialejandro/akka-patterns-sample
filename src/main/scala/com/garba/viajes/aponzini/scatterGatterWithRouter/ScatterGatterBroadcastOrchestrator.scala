package com.garba.viajes.aponzini.scatterGatterWithRouter

import akka.actor.{ActorRef, Props}
import akka.routing.BroadcastGroup
import com.garba.viajes.aponzini.common.WeatherActor
import com.garba.viajes.aponzini.common.providers.{DarkSkyActor, WundergroundActor}

case class WeatherRouterRequest()

class ScatterGatterBroadcastOrchestrator(originalSender : ActorRef) extends WeatherActor {

  val darkActor = context.actorOf(Props(new DarkSkyActor()))
  val wundergroundActor = context.actorOf(Props(new WundergroundActor()))

  override def receive = {

    case request @ WeatherRouterRequest =>
      val routees = List(darkActor, wundergroundActor).map(route => route.path.toString)
      val aggregator = context.actorOf(Props(new WeatherAggregatorWithRouter(self, routees.size)))
      val broadcastActor: ActorRef = context.actorOf(Props[Scatter].withRouter(BroadcastGroup(paths = routees)))
      broadcastActor.tell(request, aggregator)

    case aggre : AggregationResultRouter =>
      originalSender ! aggre

  }
}

class Scatter extends WeatherActor {

  override def receive = {
    case _ =>
  }
}