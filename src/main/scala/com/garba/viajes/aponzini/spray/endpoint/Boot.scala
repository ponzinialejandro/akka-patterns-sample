package com.garba.viajes.aponzini.spray.endpoint

import akka.http.scaladsl.Http
import com.garba.viajes.aponzini.common.ActorSystemContext
import kamon.Kamon

object Boot extends App with ActorSystemContext{

  Kamon.start();
  // we need an ActorSystem to host our application in
//  implicit val system: ActorSystem = ActorSystem("on-spray-can")
//  implicit val materialize: ActorMaterializer = ActorMaterializer()

  // create and start our service actor
  //val service = system.actorOf(Props[WeatherServices], "demo-service")

  // start a new HTTP server on port 8080 with our service actor as the handler

  Http().bindAndHandle(new WeatherServices().routes, "127.0.0.1", 8080)
}