package com.garba.viajes.aponzini.common

import akka.actor.{Actor, ActorSystem}
import akka.stream.ActorMaterializer

abstract class WeatherActor extends Actor with ActorSystemContext{

  def die = {
    println(s"DIE ${getClass.getName}")
    context.stop(self)
  }
}

trait ActorSystemContext{
  implicit val system = ActorSystem("weather-system")
  implicit val executionContext = system.dispatcher
  implicit val materializer = ActorMaterializer()
}
