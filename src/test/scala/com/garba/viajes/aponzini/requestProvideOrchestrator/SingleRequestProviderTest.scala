package com.garba.viajes.aponzini.requestProvideOrchestrator

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import akka.pattern.ask

import scala.concurrent.Future
import scala.concurrent.duration._


class SingleRequestProviderTest() extends TestKit(ActorSystem("SingleRequestProviderTest")) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "SingleRequestProviderTest" must {

    "Orchestrator should return an OpenWeaterMapsWithCityHistory" in {

      val sgService = system.actorOf(Props(new OpenWeatherMapActor()))
      sgService.tell(OpenWeaterMapRequest, self)
      expectMsgClass(6 seconds, classOf[OpenWeatherMapMessage])

    }
  }
}