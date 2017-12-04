package com.garba.viajes.aponzini.spray.endpoint

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._
import com.garba.viajes.aponzini.common.Person


// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class WeatherServices extends Actor with Services{

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling

  def receive = runRoute(home ~ person)
}

// this trait defines our service behavior independently from the service actor
trait Services extends HttpService {
  import com.garba.viajes.aponzini.common.JsonConverter._
  import spray.json._
  val person =
    path("/service") {
      get {
        respondWithMediaType(`application/json`) {
          complete {
            Person("Bob", "Type A", 2L).toJson.toString()
          }
        }
      }
    }

  val home = {
    path("/") {
      get {
        // respond with text/html.
        respondWithMediaType(`text/html`) {
          complete {
            // respond with a set of HTML elements
            <html>
              <body>
                <h1>HOME</h1>
              </body>
            </html>
          }
        }
      }
    }
  }
}