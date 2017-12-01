package com.garba.viajes.aponzini.common.providers

import akka.http.scaladsl.model.HttpResponse
import com.garba.viajes.aponzini.common.{AbstractWheatherModel, Providers, WeatherServiceProvider}

case class DarkSkyMessage(override val model : String) extends AbstractWheatherModel

class DarkSkyActor() extends WeatherServiceProvider with Providers {


  val serviceUrl : String = darkSkyUrl

  override def getWeatherService = defaultServiceCall(serviceUrl)

  override def transformModel(response: HttpResponse) = defaultStringResultConvertion(response)

  override def getNext() = sender()

  override def wrapInMessage(model: String) = DarkSkyMessage(model)

  override def getProviderName = "Dark Sky"
}
