package com.garba.viajes.aponzini.common


import spray.json._

object JsonConverter extends DefaultJsonProtocol {
  implicit val personFormat = jsonFormat3(Person)
}

case class Person(name: String, fistName: String, age: Long)



