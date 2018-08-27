package com.garba.viajes.aponzini.scatterGatter

import akka.actor.Props
import com.garba.viajes.aponzini.common.WeatherActor

class ScatterGatterRequester extends WeatherActor{

  override def receive = {
    case message @ ScatterGatterRequest =>
      println(" ScatterGatterRequester => receive ScatterGatterRequest")
      println(" sends ScatterGatterRequest to ScatterGatterOrquestrator")
      val orquestrator = context.actorOf(Props(new ScatterGatterOrquestrator(sender())))
      orquestrator ! message
  }
}
