package com.garba.viajes.aponzini.requests

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import akka.pattern.ask
import com.garba.viajes.aponzini.common.providers.{OpenWeaterMapRequest, OpenWeatherMapActor, OpenWeatherMapMessage}

import scala.concurrent.Future
import scala.concurrent.duration._


class SingleRequestProviderTest() extends TestKit(ActorSystem("SingleRequestProviderTest")) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "SingleRequestProviderTest" must {

    "Single request OpenWeatherMapActor" in {

      val sgService = system.actorOf(Props(new OpenWeatherMapActor()))
      sgService ! OpenWeaterMapRequest
      expectMsgClass(6 seconds, classOf[OpenWeatherMapMessage])
    }
  }
}