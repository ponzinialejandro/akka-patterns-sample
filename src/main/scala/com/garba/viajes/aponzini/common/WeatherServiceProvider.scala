package com.garba.viajes.aponzini.common

import akka.actor.ActorRef
import akka.http.scaladsl.model.HttpResponse

import scala.concurrent.Future

trait AbstractWheatherModel {
  def model: String
}

abstract class WeatherServiceProvider extends WeatherActor {

  def getWeatherService : Future[HttpResponse]
  def transformModel(response : HttpResponse)    : Future[String]
  def getNext() : ActorRef
  def wrapInMessage(model: String) : AbstractWheatherModel

  override def receive = {
    case _ => {
      getWeatherService.flatMap(transformModel).onComplete( model =>{
        getNext() ! wrapInMessage(model.getOrElse("Ups wrong"))
      })
    }
  }

}