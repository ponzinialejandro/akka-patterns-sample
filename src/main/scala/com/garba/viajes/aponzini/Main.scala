package com.garba.viajes.aponzini

import akka.actor.{Actor, ActorSystem, Props}

object Main {

  def main(args: Array[String]) {

    implicit val system = ActorSystem("my-system")

    val darkActor = system.actorOf(Props( new DarkSkyActor(null)))
    val wundergroundActor = system.actorOf(Props( new WundergroundActor(null)))

    darkActor ! None
    wundergroundActor ! None
  }
}