package com.garba.viajes.aponzini

import akka.actor.{Actor, ActorRef, ActorSystem, Props}


object Main extends App {

    implicit val system = ActorSystem("my-system")
    //Simulo un web service
    val sgService = system.actorOf(Props( new ScatterGatterService()))
    sgService ! ScatterGatterRequest

}