package com.garba.viajes.aponzini

import akka.actor.ActorRef
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal

case class WundergroundMessage(model: String) extends AbstractWheatherModel

class WundergroundActor(next: ActorRef) extends WeatherServiceProvider {

  val apiUrl = "http://api.wunderground.com/api/"
  val apiKey = "6ea16487adb7c96d"
  val serviceUrl = apiUrl + apiKey + "/conditions/q/ar/buenos-aires.json"

  override def getWeatherService = Http().singleRequest(HttpRequest(uri = serviceUrl))

  override def transformModel(response: HttpResponse) = Unmarshal(response).to[String]

  override def getNext() = next

  override def wrapInMessage(model: String) = WundergroundMessage(model)
}
