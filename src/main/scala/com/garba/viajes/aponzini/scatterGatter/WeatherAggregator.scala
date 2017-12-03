package com.garba.viajes.aponzini.scatterGatter

import akka.actor.ActorRef
import akka.util.Timeout
import com.garba.viajes.aponzini.common.WeatherActor
import com.garba.viajes.aponzini.common.providers.{DarkSkyMessage, WundergroundMessage}

import scala.concurrent.duration._

case class AggregationResult(map : Map[String,String])

class WeatherAggregator(next: ActorRef, timeout : FiniteDuration) extends WeatherActor{

  var weather  : Map[String,String] =  Map[String,String]()
  override def preStart(): Unit = context.system.scheduler.scheduleOnce(timeout, self, Timeout)


  override def receive = {
    case DarkSkyMessage(model) =>{
      println("WeatherAggregator.DarkSkyMessage")
     weather += ("DarkSkyMessage" -> model)
    }
    case WundergroundMessage(model) => {
      println("WeatherAggregator.WundergroundMessage")
      weather += ("WundergroundMessage" -> model)
  }
    case Timeout => {
      println("WeatherAggregator.Timeout")
      next ! AggregationResult(weather)
    }
    case _ => {
      println("WeatherAggregator.Error")
      println("error")
    }
  }
}
