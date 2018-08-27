package com.garba.viajes.aponzini.common

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal

import scala.concurrent.Future

trait Providers extends ActorSystemContext{

  def openWeatherUrl : String =  {
    val apiKey = "42ba482614aaf1991cff199aed0159b6"
    val city = 3435907
    val apiUrl = "http://api.openweathermap.org/data/2.5/forecast"
    val serviceUrl = s"${apiUrl}?id=${city}&APPID=${apiKey}"
    serviceUrl
  }

  def darkSkyUrl : String =  {
    val apiKey = "9c90360fd2a66589c0200eda7b323db9"
    val latitude = -34.6083
    val longitude = -58.3712
    val apiUrl = "https://api.darksky.net/forecast/"
    val serviceUrl = s"${apiUrl}${apiKey}/${latitude},${longitude}"
    serviceUrl
  }

  def wundergroundUrl : String ={
    val apiUrl = "http://api.wunderground.com/api/"
    val apiKey = "6ea16487adb7c96d"
    val serviceUrl = s"${apiUrl}${apiKey}/conditions/q/ar/buenos-aires.json"
    serviceUrl
  }
}
