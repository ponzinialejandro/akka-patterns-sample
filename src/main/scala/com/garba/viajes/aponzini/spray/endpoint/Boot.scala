package com.garba.viajes.aponzini.spray.endpoint

import akka.actor._
import akka.actor.{ActorSystem, Props}
import akka.io.IO
import kamon.Kamon
import spray.can.Http

object Boot extends App {

  Kamon.start()
  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("on-spray-can")

  // create and start our service actor
  val service = system.actorOf(Props[WeatherServices], "demo-service")

  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ! Http.Bind(service, interface = "localhost", port = 8080)
}