package com.garba.viajes.aponzini.scatterGatterWithRouter

import akka.actor.Props
import com.garba.viajes.aponzini.common.WeatherActor

class ScatterGatterBroadcastRequester extends WeatherActor{
  override def receive = {
    case message @ WeatherRouterRequest =>
      val orginalSender =  sender()
      val orchestrator = context.actorOf(Props(new ScatterGatterBroadcastOrchestrator(orginalSender)))
      orchestrator ! message

  }
}
