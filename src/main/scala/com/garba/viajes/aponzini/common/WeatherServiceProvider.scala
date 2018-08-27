package com.garba.viajes.aponzini.common

import akka.actor.ActorRef
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.pattern.pipe

import scala.concurrent.Future

trait AbstractWheatherModel {
  def model: String
}

abstract class WeatherServiceProvider extends WeatherActor {

  def serviceUrl : String

  def getProviderName : String

  def getWeatherService: Future[HttpResponse] = defaultServiceCall(serviceUrl)

  def transformModel(response: HttpResponse): Future[String] = defaultStringResultConvertion(response)

  def getNext(): ActorRef = sender()

  def wrapInMessage(model: String): AbstractWheatherModel


  def defaultServiceCall(url : String): Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = url))
  def defaultStringResultConvertion(response: HttpResponse) : Future[String] = Unmarshal(response).to[String]

  override def receive = {
    case _ => {
        println("Receive message from " + getProviderName)
      pipe { //Enviar el resultado de este future al siguiente actor
        getWeatherService //devuelve un stream de httResponse
          .flatMap(transformModel) //Lo transforma en u String
          .map(model => wrapInMessage(model)) //Lo wrapea en un
      } to getNext()

      /*
      Esta es una version menos elegante, requiere almacenar el sender para que luego sea accesible desde el contexto
      de la funcion anonima. La ejecucion del callback onComplete, se ejecuta en otro thread
      val theSender = getNext()
      getWeatherService.flatMap(transformModel).onComplete( model =>{
        theSender ! wrapInMessage(model.getOrElse("Ups wrong"))
      })

        def flatMap[S](f: (T) ⇒ Future[S])(implicit executor: ExecutionContext): Future[S]
        Creates a new future by applying a function to the successful result of
        this future, and returns the result of the function as the new future.
        If this future is completed with an exception then the new future will
        also contain this exception.

        def map[S](f: (T) ⇒ S)(implicit executor: ExecutionContext): Future[S]
        Creates a new future by applying a function to the successful result of
        this future. If this future is completed with an exception then the new
        future will also contain this exception
       */
    }
  }

}