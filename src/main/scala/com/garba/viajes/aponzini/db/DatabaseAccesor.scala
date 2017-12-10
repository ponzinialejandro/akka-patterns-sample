package com.garba.viajes.aponzini.db

import akka.actor.Props
import akka.routing.{DefaultResizer, RoundRobinPool}
import com.garba.viajes.aponzini.common.WeatherActor

class DatabaseAccesor extends WeatherActor {

  lazy val resizer = DefaultResizer(lowerBound = 2, upperBound = 5)
  lazy val workers = context.actorOf(Props[DatabaseWorker]
    .withDispatcher("db-dispatcher")
    .withRouter(RoundRobinPool(nrOfInstances = 3, Some(resizer))))

  override def receive = {

    case request => {
      workers.forward(request)
    }

  }
}
