package com.garba.viajes.aponzini.scatterGatterWithRouter

import akka.actor.ActorRef
import akka.util.Timeout
import com.garba.viajes.aponzini.common.WeatherActor
import com.garba.viajes.aponzini.common.providers.{DarkSkyMessage, WundergroundMessage}

import scala.concurrent.duration._
import com.garba.viajes.aponzini.scatterGatter.AggregationResult

case class AggregationResultRouter(map : Map[String,String])

case class AggregationRouterTimeout()

class WeatherAggregatorWithRouter(next: ActorRef, providers: Int) extends WeatherActor {

  var weather  : Map[String,String] =  Map[String,String]()
  val timeout = 40 seconds
  val task = context.system.scheduler.scheduleOnce(timeout, self, AggregationRouterTimeout)
  var count : Int = 0


  def send() = {
    next ! AggregationResultRouter(weather)
  }
  def checkSend() = {
    count += 1
    if(count == providers){
      send()
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

    case AggregationRouterTimeout => {
      println("WeatherAggregator.Timeout")
      send()
    }

    case otroCaso => {
      println("WeatherAggregator.Error" + otroCaso.getClass.getName)
      println("error")
    }
  }
}
