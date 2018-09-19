package com.garba.viajes.aponzini.common

import akka.actor.{Actor, ActorLogging, ActorSystem}
import akka.stream.ActorMaterializer

abstract class WeatherActor extends Actor with ActorSystemContext with ActorLogging {


  override def preStart(): Unit = {
    log.info("Starting actor {} #{}", this.getClass.getSimpleName , this.hashCode())
  }

  override def postStop(): Unit = {
    log.info("Stopping actor {} #{}", this.getClass.getSimpleName , this.hashCode())
  }

  def die = {
    println(s"DIE ${getClass.getName}")
    context.stop(self)
  }
}

trait ActorSystemContext{
  implicit val system = ActorSystemContextSingleton.system
  implicit val executionContext = ActorSystemContextSingleton.executionContext
  implicit val materializer = ActorSystemContextSingleton.materializer
}

object ActorSystemContextSingleton {
  implicit val system = ActorSystem("weather-system")
  implicit val executionContext = system.dispatcher
  implicit val materializer = ActorMaterializer()
}
