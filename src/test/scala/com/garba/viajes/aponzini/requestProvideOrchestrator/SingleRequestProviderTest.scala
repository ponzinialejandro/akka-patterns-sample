package com.garba.viajes.aponzini.requestProvideOrchestrator

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.concurrent.duration._

class SingleRequestProviderTest() extends TestKit(ActorSystem("SingleRequestProviderTest")) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "ScatterGatterService" must {

    "return an AggregationResult class" in {
      val sgService = system.actorOf(Props(new RequestOrchestrator(self)))
      sgService.tell(WeaterRequest, self) //implicit sender
      expectMsgClass(6 seconds , classOf[OpenWeaterMapsWithCityHistory])
    }
  }
}