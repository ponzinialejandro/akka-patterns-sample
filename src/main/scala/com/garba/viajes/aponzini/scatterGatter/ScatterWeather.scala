package com.garba.viajes.aponzini.scatterGatter

import akka.actor.{Actor, ActorRef}

class ScatterWeather(services :Seq[ActorRef])  extends Actor {

  override def receive = {
      //sender() is the aggregator
      //Envia un mensaje al servicio para que traiga los datos de weather
      case _ => services.foreach( _.tell(None, sender()))
  }
}