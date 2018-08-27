package com.garba.viajes.aponzini.singleRequest

import akka.actor.Props
import com.garba.viajes.aponzini.common.WeatherRequesterActor
import com.garba.viajes.aponzini.common.providers.DarkSkyActor

class DarkSkyRequester extends WeatherRequesterActor {

  override def receive = {
    case _ =>
      val next = context.actorOf(Props(new DarkSkyActor))
      next.forward(None)
  }
}
