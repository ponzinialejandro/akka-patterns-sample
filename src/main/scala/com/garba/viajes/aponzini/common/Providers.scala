package com.garba.viajes.aponzini.common

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal

import scala.concurrent.Future

trait Providers extends ActorSystemContext{


  def openWeatherUrl =  {
    val apiKey = "42ba482614aaf1991cff199aed0159b6"
    val city = 3435907
    val apiUrl = "http://api.openweathermap.org/data/2.5/forecast"
    val serviceUrl = apiUrl + "?id=" + city+ "&APPID=" +apiKey
    serviceUrl
  }

  def darkSkyUrl =  {
    val apiKey = "9c90360fd2a66589c0200eda7b323db9"
    val latitude = -34.6083
    val longitude = -58.3712
    val apiUrl = "https://api.darksky.net/forecast/"
    val serviceUrl = apiUrl + apiKey + "/" + latitude + "," + longitude
    serviceUrl
  }

  def wundergroundUrl ={
    val apiUrl = "http://api.wunderground.com/api/"
    val apiKey = "6ea16487adb7c96d"
    val serviceUrl = apiUrl + apiKey + "/conditions/q/ar/buenos-aires.json"
    serviceUrl
  }

  def defaultServiceCall(url : String): Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = url))
  def defaultStringResultConvertion(response: HttpResponse) : Future[String] = Unmarshal(response).to[String]
}
