package com.garba.viajes.aponzini

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import akka.actor.{ActorSystem, Actor}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.http.scaladsl.model.HttpRequest

import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling._

import scala.concurrent.Future
import scala.util.{ Failure, Success }

object Main {

  def main(args: Array[String]) {

    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val apiKey = "9c90360fd2a66589c0200eda7b323db9"
    val latitude = -34.6083
    val longitude = -58.3712
    val apiUrl = "https://api.darksky.net/forecast/"
    val serviceUrl = apiUrl + apiKey + "/" + latitude + "," + longitude



    val httpResponse: Future[HttpResponse] =
      Http().singleRequest(HttpRequest(uri = serviceUrl))

    val futureBody : Future[String] = httpResponse.flatMap(response => {
      Unmarshal(response).to[String]
    })

    futureBody
      .onComplete {
        case Success(res) => {
          println(res)
        }
        case Failure(_)   => sys.error("something wrong")
      }

  }
}