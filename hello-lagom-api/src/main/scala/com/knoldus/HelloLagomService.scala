package com.knoldus

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.Service.{named, pathCall, topic}
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.api.broker.kafka.{KafkaProperties, PartitionKeyStrategy}
import com.lightbend.lagom.scaladsl.api.transport.Method
import play.api.libs.json.{Format, Json}


case class Employee(name: String,age: Int)

object Employee {
  implicit val format: Format[Employee] = Json.format[Employee]
}
trait HelloLagomService extends Service {

  def hello(id: String): ServiceCall[NotUsed, String]

  def testUser(id : Int): ServiceCall[NotUsed,UserData]
  def deleteEmployee(name: String): ServiceCall[NotUsed, Done]
  def addEmployee(): ServiceCall[Employee, Done]
  def updateEmployee(): ServiceCall[Employee, String]
  def getEmployee(name: String): ServiceCall[NotUsed, List[Employee]]
  def print(id: String): ServiceCall[NotUsed, String]



  override final def descriptor = {
    import Service._
    // @formatter:off
    named("hello-lagom")
      .withCalls(
        pathCall("/api/hello/:id", hello _),
     //   pathCall("/api/hello/:id", useGreeting _),
        pathCall("/api/print/:id", print _),
        restCall(Method.POST,   "/employee/add",addEmployee _),
        pathCall("/employee/:name", getEmployee _),
        restCall(Method.DELETE, "/employee/delete/:name", deleteEmployee _),
        restCall(Method.PUT, "/employee/update", updateEmployee _),
        pathCall("/haha/haha/:id",testUser _)
      )
      .withAutoAcl(true)
    // @formatter:on
  }
}

