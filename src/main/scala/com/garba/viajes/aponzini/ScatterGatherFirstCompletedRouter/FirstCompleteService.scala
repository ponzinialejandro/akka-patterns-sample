package com.garba.viajes.aponzini.ScatterGatherFirstCompletedRouter

import akka.actor.{ActorRef, Props}
import akka.routing.{ ScatterGatherFirstCompletedGroup}
import com.garba.viajes.aponzini.common.WeatherActor
import com.garba.viajes.aponzini.common.providers.{DarkSkyActor, WundergroundActor}
import scala.concurrent.duration._

case class FirstCompleteRequest()

class FirstCompleteService(originalSender : ActorRef) extends WeatherActor{

  val darkActor = context.actorOf(Props(new DarkSkyActor()))
  val wundergroundActor = context.actorOf(Props(new WundergroundActor()))

  override def receive = {
    case request@FirstCompleteRequest =>
      val routees = List(darkActor, wundergroundActor).map(route => route.path.toString)

      val firstCompleteActorRouter : ActorRef = context.actorOf(Props[FirstCompleteRouter].withRouter(ScatterGatherFirstCompletedGroup(paths = routees, within = 8 seconds)) )
      firstCompleteActorRouter ! request

    case message =>
      originalSender ! message
  }

}

class FirstCompleteRouter extends WeatherActor {

  override def receive = {
    case _ =>
  }
}