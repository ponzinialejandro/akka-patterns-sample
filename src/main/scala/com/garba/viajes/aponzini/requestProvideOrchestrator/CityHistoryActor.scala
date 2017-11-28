package com.garba.viajes.aponzini.requestProvideOrchestrator

import com.garba.viajes.aponzini.scattergatter.WeatherActor

case class OpenWeaterMapsWithCityHistory(weather: String, history :String)

class CityHistoryActor extends WeatherActor {

  val bairesHistory :String  = "La Ciudad de Buenos Aires fue fundada por primera vez en 1536"

  override def receive = {
    case OpenWeatherMapMessage(model) => sender ! OpenWeaterMapsWithCityHistory(weather = model, history = bairesHistory)
  }
}
