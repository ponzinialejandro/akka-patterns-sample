package com.garba.viajes.aponzini.scatterGatterWithRouter

import akka.actor.ActorRef
import akka.util.Timeout
import com.garba.viajes.aponzini.common.WeatherActor
import com.garba.viajes.aponzini.common.providers.{DarkSkyMessage, WundergroundMessage}

import scala.concurrent.duration._
import com.garba.viajes.aponzini.scattergatter.AggregationResult

case class AggregationResult(map : Map[String,String])

class WeatherAggregatorWithRouter(next: ActorRef, providers: Int) extends WeatherActor {

  var weather  : Map[String,String] =  Map[String,String]()
  val timeout = 5 seconds
  val task = context.system.scheduler.scheduleOnce(timeout, self, Timeout)
  var count : Int = 0

  def checkSend() = {
    count += 1
    if(count == providers){
      next ! AggregationResult(weather)
      task.cancel()
    }
  }

  override def receive = {
    case DarkSkyMessage(model) =>{
      println("WeatherAggregator.DarkSkyMessage")
      weather += ("DarkSkyMessage" -> model)
      checkSend()
    }

    case WundergroundMessage(model) => {
      println("WeatherAggregator.WundergroundMessage")
      weather += ("WundergroundMessage" -> model)
      checkSend()
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
