package com.garba.viajes.aponzini.common

import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy.Restart
import scala.concurrent.duration._
object SupervisionWeatherStrategies {

    
  lazy val strategy = OneForOneStrategy(maxNrOfRetries = 3, withinTimeRange = 4 seconds) {
    case _: Exception => Restart
    case _ => Restart
  }


}
