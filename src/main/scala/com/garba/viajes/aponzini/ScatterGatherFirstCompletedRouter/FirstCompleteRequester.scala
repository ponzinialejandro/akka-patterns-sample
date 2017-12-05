package com.garba.viajes.aponzini.ScatterGatherFirstCompletedRouter

import akka.actor.Props
import com.garba.viajes.aponzini.common.WeatherActor

class FirstCompleteRequester extends WeatherActor{

  override def receive = {
    case message @ FirstCompleteRequest =>
      val serivce = context.actorOf(Props(new FirstCompleteService(sender())))
      serivce ! message
  }
}
