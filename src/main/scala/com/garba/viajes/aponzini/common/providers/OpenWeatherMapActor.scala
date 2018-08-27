package com.garba.viajes.aponzini.common.providers

import com.garba.viajes.aponzini.common.{AbstractWheatherModel, Providers, WeatherServiceProvider}


case class OpenWeaterMapRequest()
case class OpenWeatherMapMessage(override val model : String) extends AbstractWheatherModel

class OpenWeatherMapActor extends WeatherServiceProvider with Providers{

  override def serviceUrl = openWeatherUrl

  override def wrapInMessage(model: String) = OpenWeatherMapMessage(model)

  override def getProviderName = "Open Weather Map"
}
