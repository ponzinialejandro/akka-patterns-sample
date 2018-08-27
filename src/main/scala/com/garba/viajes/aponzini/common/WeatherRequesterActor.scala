package com.garba.viajes.aponzini.common

import akka.actor.SupervisorStrategy

abstract class WeatherRequesterActor extends  WeatherActor{
  override def supervisorStrategy: SupervisorStrategy = SupervisionWeatherStrategies.strategy
}
