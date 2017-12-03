package com.garba.viajes.aponzini.FirstComplete

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import com.garba.viajes.aponzini.ScatterGatherFirstCompletedRouter.{FirstCompleteRequest, FirstCompleteService}
import com.garba.viajes.aponzini.common.providers.{DarkSkyMessage, WundergroundMessage}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.concurrent.duration._

class FirstCompleteTest extends TestKit(ActorSystem("SingleRequestProviderTest")) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "SingleRequestProviderTest" must {
    "Orchestrator should return an OpenWeaterMapsWithCityHistory" in {
      val sgService = system.actorOf(Props( new FirstCompleteService(self)))
      sgService ! FirstCompleteRequest //implicit sender
      expectMsgPF(10 seconds ){
        case WundergroundMessage(_) =>
        case DarkSkyMessage(_) =>
        case _ => fail()
      }
    }
  }
}
