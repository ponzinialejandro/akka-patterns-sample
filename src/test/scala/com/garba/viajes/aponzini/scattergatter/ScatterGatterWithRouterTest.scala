package com.garba.viajes.aponzini.scattergatter

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import com.garba.viajes.aponzini.scatterGatterWithRouter.{AggregationResultRouter, ScatterGatterServiceWithRouter, WeatherRouterRequest}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.concurrent.duration._

class ScatterGatterWithRouterTest() extends TestKit(ActorSystem("ScatterGatherRouterTest")) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "ScatterGatterRouterService" must {

    "return an AggregationResult class" in {
      val sgService = system.actorOf(Props( new ScatterGatterServiceWithRouter(self)))
      sgService ! WeatherRouterRequest
      expectMsgClass(6 seconds , classOf[AggregationResultRouter])
    }
  }
}