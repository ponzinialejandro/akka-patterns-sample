package com.garba.viajes.aponzini

import akka.actor.{Actor, ActorRef, ActorSystem}
import akka.util.Timeout
import com.garba.viajes.aponzini.{DarkSkyMessage, WundergroundMessage}

import scala.concurrent.duration._

case class AggregationResult(map : Map[String,String])

class WeatherAggregator(next: ActorRef, timeout : FiniteDuration) extends Actor{

  var weather  : Map[String,String] =  Map[String,String]()
  implicit val system = ActorSystem("my-system")
  implicit val executionContext = system.dispatcher

  override def preStart(): Unit = context.system.scheduler.scheduleOnce(timeout, self, Timeout)


  override def receive = {
    case DarkSkyMessage(model) =>{
     weather += ("DarkSkyMessage" -> model)
    }
    case WundergroundMessage(model) => {
      weather += ("WundergroundMessage" -> model)
  }
    case Timeout => {
      next ! AggregationResult(weather)
    }
    case _ => {
      println("error")
    }
  }
}
