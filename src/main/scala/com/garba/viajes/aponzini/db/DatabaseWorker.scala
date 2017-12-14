package com.garba.viajes.aponzini.db

import com.garba.viajes.aponzini.common.WeatherActor

import scala.concurrent.Future

class DatabaseWorker extends WeatherActor {

  override def receive = {
    case _ =>
      println("DB WORKER "+ hashCode())
      Future {
        println("DB WORKING...")
        Thread.sleep(2000)
        println("Mocking saved in another thread!")
      }
  }

}
