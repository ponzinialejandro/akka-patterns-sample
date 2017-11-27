package com.garba.viajes.aponzini

import scala.concurrent.Future
import scala.util.{Failure, Success}
import akka.actor.{Actor, ActorRef, ActorSystem}
import akka.http.scaladsl.model.HttpResponse
import akka.stream.ActorMaterializer

trait AbstractWheatherModel {
  def model: String
}

abstract class WeatherServiceProvider extends Actor {

  implicit val system = ActorSystem("my-system")
  implicit val executionContext = system.dispatcher
  implicit val materializer = ActorMaterializer()

  def getWeatherService : Future[HttpResponse]
  def transformModel(response : HttpResponse)    : Future[String]
  def getNext() : ActorRef
  def wrapInMessage(model: String) : AbstractWheatherModel

  override def receive = {
    case _ => {
      getWeatherService.flatMap(transformModel).onComplete( model =>{
        //println(model.get)
        getNext() ! wrapInMessage(model.getOrElse("Ups wrong"))
      })
    }
  }

}