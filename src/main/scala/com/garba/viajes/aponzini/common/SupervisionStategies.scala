package com.garba.viajes.aponzini.common

import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy.Restart

object SupervisionStategies {

    
  lazy val stop = OneForOneStrategy() {
    case _: Exception => Restart
    case _ => Restart
  }


}
