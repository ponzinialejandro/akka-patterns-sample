package com.garba.viajes.aponzini.requestProvideOrchestrator

import akka.http.scaladsl.model.{HttpResponse}
import akka.pattern.pipe
import com.garba.viajes.aponzini.common.{Providers, WeatherActor}
import com.garba.viajes.aponzini.scattergatter.AbstractWheatherModel

import scala.concurrent.Future

case class OpenWeaterMapRequest()
case class OpenWeatherMapMessage(override val model : String) extends AbstractWheatherModel

class OpenWeatherMapActor extends WeatherActor with Providers{

  val serviceUrl = openWeatherUrl

  override def receive = {
    case OpenWeaterMapRequest =>
      def consultProvider() : Future[HttpResponse] = defaultServiceCall(serviceUrl)
      def convertResonse(response :HttpResponse) :Future[String] = defaultStringResultConvertion(response)
      def resturnInWrapper(rs : String) : AbstractWheatherModel = OpenWeatherMapMessage(rs)
      pipe{
        consultProvider()
          .flatMap(convertResonse)
          .map(resturnInWrapper)
      } to sender()
  }
}
