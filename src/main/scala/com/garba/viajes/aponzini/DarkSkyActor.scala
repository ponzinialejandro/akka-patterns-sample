package com.garba.viajes.aponzini

import akka.actor.{Actor, ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer


case class DarkSkyMessage(override val model : String) extends AbstractWheatherModel

class DarkSkyActor(next: ActorRef) extends WeatherServiceProvider {


  val apiKey = "9c90360fd2a66589c0200eda7b323db9"
  val latitude = -34.6083
  val longitude = -58.3712
  val apiUrl = "https://api.darksky.net/forecast/"
  val serviceUrl = apiUrl + apiKey + "/" + latitude + "," + longitude

  override def getWeatherService =  Http().singleRequest(HttpRequest(uri = serviceUrl))

  override def transformModel(response: HttpResponse) = Unmarshal(response).to[String]

  override def getNext() = next

  override def wrapInMessage(model: String) = DarkSkyMessage(model)
}
