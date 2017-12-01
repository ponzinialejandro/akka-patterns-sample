package com.garba.viajes.aponzini.common.providers

import akka.http.scaladsl.model.HttpResponse
import com.garba.viajes.aponzini.common.{AbstractWheatherModel, Providers, WeatherServiceProvider}


case class OpenWeaterMapRequest()
case class OpenWeatherMapMessage(override val model : String) extends AbstractWheatherModel

class OpenWeatherMapActor extends WeatherServiceProvider with Providers{

  val serviceUrl = openWeatherUrl

  override def getWeatherService = defaultServiceCall(serviceUrl)

  override def transformModel(response: HttpResponse) = defaultStringResultConvertion(response)

  override def getNext() = sender()

  override def wrapInMessage(model: String) = OpenWeatherMapMessage(model)
}
