package com.garba.viajes.aponzini.common

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.garba.viajes.aponzini.db.DatabaseAccesor

object ActorReferences {

  val actors: scala.collection.mutable.Map[String, ActorRef] = scala.collection.mutable.Map()

  lazy val dbsystem = ActorSystem("database-system")

  def getDatabaseAccesor : ActorRef = {
    val actor: ActorRef = actors.get(WeatherActorSystems.DB_ACCESOR) match {
      case None => {
        val newActor = dbsystem.actorOf(Props(new DatabaseAccesor))
        actors.put(WeatherActorSystems.DB_ACCESOR, newActor)
        newActor
      }
      case valor => valor.get
    }
    actor
  }
}

object WeatherActorSystems {
  val DB_ACCESOR = "db-accesor"
}
