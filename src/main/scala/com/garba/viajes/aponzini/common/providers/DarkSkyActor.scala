package com.garba.viajes.aponzini.common.providers

import com.garba.viajes.aponzini.common.{AbstractWheatherModel, Providers, WeatherServiceProvider}

case class DarkSkyMessage(override val model : String) extends AbstractWheatherModel

class DarkSkyActor() extends WeatherServiceProvider with Providers {

  override def serviceUrl = darkSkyUrl

  override def wrapInMessage(model: String) = DarkSkyMessage(model)

  override def getProviderName = "Dark Sky"
}
