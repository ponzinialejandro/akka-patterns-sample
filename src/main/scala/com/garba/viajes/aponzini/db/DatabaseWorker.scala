package com.garba.viajes.aponzini.db

import com.garba.viajes.aponzini.common.WeatherActor

class DatabaseWorker extends WeatherActor {

  override def receive = {
    case asd =>
      println("ENTRA al DB WORKER")
  }

}
