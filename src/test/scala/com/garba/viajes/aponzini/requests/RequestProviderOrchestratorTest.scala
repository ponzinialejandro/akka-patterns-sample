package com.garba.viajes.aponzini.requests

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import com.garba.viajes.aponzini.requestProvideOrchestrator.{OpenWeaterMapsWithCityHistory, RequestOrchestrator, WeaterRequest}

import scala.concurrent.duration._

class RequestProviderOrchestratorTest extends TestKit(ActorSystem("SingleRequestProviderTest")) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "SingleRequestProviderTest" must {

    "Orchestrator should return an OpenWeaterMapsWithCityHistory" in {
      val sgService = system.actorOf(Props(new RequestOrchestrator(self)))
      sgService ! WeaterRequest  //implicit sender
      expectMsgClass(6 seconds, classOf[OpenWeaterMapsWithCityHistory])
    }
  }
}