package com.garba.viajes.aponzini.common.providers

import com.garba.viajes.aponzini.common.{AbstractWheatherModel, Providers, WeatherServiceProvider}

case class WundergroundMessage(model: String) extends AbstractWheatherModel

class WundergroundActor() extends WeatherServiceProvider with Providers{

  val serviceUrl = wundergroundUrl

  override def wrapInMessage(model: String) = WundergroundMessage(model)

  override def getProviderName = "Wunderground"
}
