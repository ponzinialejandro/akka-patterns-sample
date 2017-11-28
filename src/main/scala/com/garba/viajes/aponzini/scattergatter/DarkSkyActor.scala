package com.garba.viajes.aponzini.scattergatter

import akka.actor.ActorRef
import akka.http.scaladsl.model.{HttpResponse}
import com.garba.viajes.aponzini.common.Providers

case class DarkSkyMessage(override val model : String) extends AbstractWheatherModel

class DarkSkyActor(next: ActorRef) extends WeatherServiceProvider with Providers {


  val serviceUrl : String = darkSkyUrl

  override def getWeatherService = defaultServiceCall(serviceUrl)

  override def transformModel(response: HttpResponse) = defaultStringResultConvertion(response)

  override def getNext() = next

  override def wrapInMessage(model: String) = DarkSkyMessage(model)
}
