package com.garba.viajes.aponzini.requestProvideOrchestrator

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.pattern.pipe
import com.garba.viajes.aponzini.scattergatter.{AbstractWheatherModel, WeatherActor}

import scala.concurrent.Future

case class OpenWeaterMapRequest()
case class OpenWeatherMapMessage(override val model : String) extends AbstractWheatherModel

class OpenWeatherMapActor extends WeatherActor{

  //http://api.openweathermap.org/data/2.5/forecast?id=3435907&APPID=42ba482614aaf1991cff199aed0159b6

  val apiKey = "42ba482614aaf1991cff199aed0159b6"
  val city = 3435907
  val apiUrl = "http://api.openweathermap.org/data/2.5/forecast"
  val serviceUrl = apiUrl + "?id=" + city+ "&APPID=" +apiKey

  override def receive = {
    case OpenWeaterMapRequest =>
      def consultProvider() : Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = apiUrl))
      def convertResonse(response :HttpResponse) :Future[String] = Unmarshal(response).to[String]
      def resturnInWrapper(rs : String) : AbstractWheatherModel = OpenWeatherMapMessage(rs)
      pipe{
        consultProvider()
          .flatMap(convertResonse)
          .map(resturnInWrapper)
      } to sender()
  }
}
