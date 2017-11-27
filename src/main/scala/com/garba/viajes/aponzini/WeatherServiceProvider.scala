package com.garba.viajes.aponzini

import scala.concurrent.Future
import scala.util.{Failure, Success}
import akka.actor.{Actor, ActorRef, ActorSystem}
import akka.http.scaladsl.model.HttpResponse

trait AbstractWheatherModel {
  def model: String
}

abstract class WeatherServiceProvider extends Actor {


  def getWeatherService : Future[HttpResponse]
  def transformModel(response : HttpResponse)    : Future[String]
  def getNext() : ActorRef
  def wrapInMessage(data: String) : AbstractWheatherModel

  override def receive = {
    case _ => {
      getWeatherService.flatMap(transformModel).onComplete( model =>{
        getNext() ! wrapInMessage(model.getOrElse("Ups wrong"))
      })
    }
  }

}