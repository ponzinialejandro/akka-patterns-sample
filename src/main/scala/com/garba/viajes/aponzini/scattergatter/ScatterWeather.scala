package com.garba.viajes.aponzini.scattergatter

import akka.actor.{Actor, ActorRef}

class ScatterWeather(services :Seq[ActorRef])  extends Actor {

  override def receive = {
      //Envia un mensaje al servicio para que traiga los datos de weather
      case _ => services.foreach(_ ! None)
  }
}
