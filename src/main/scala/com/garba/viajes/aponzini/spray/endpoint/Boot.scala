package com.garba.viajes.aponzini.spray.endpoint

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import kamon.Kamon
import kamon.statsd.StatsDReporter

object Boot extends App {

  Kamon.addReporter(new StatsDReporter)
  // we need an ActorSystem to host our application in
  implicit val system: ActorSystem = ActorSystem("on-spray-can")
  implicit val materialize: ActorMaterializer = ActorMaterializer()

  // create and start our service actor
  //val service = system.actorOf(Props[WeatherServices], "demo-service")

  // start a new HTTP server on port 8080 with our service actor as the handler
  //IO(Http) ! Http.Bind(service, interface = "localhost", port = 8080)

  Http().bindAndHandle(new WeatherServices().routes, "127.0.0.1", 8080)
}