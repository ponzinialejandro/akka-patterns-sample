package com.garba.viajes.aponzini.db

import akka.actor.Props
import akka.routing.FromConfig
import com.garba.viajes.aponzini.common.WeatherActor

class DatabaseAccesor extends WeatherActor {

  lazy val workers = context.actorOf(Props[DatabaseWorker]
    .withDispatcher("db-dispatcher")
    .withRouter(FromConfig), "workers")

  override def receive = {

    case request => {
      workers.forward(request)
    }

  }
}
