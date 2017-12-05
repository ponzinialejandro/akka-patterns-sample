package com.garba.viajes.aponzini.requests

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import com.garba.viajes.aponzini.requestProvideOrchestrator.{CityHistoryWeatherRequester, OpenWeaterMapsWithCityHistory, RequestOrchestrator, WeatherHistoryRequest}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import scala.concurrent.duration._

class WeatherRequestTest extends TestKit(ActorSystem("SingleRequestProviderTest")) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "Weather Request test" must {

    "should responde with " in {
      val sgService = system.actorOf(Props(new CityHistoryWeatherRequester()))
      sgService ! WeatherHistoryRequest  //implicit sender
      expectMsgClass(15 seconds, classOf[OpenWeaterMapsWithCityHistory])
    }
  }
}