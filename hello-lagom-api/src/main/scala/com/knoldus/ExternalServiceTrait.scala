package com.knoldus

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import play.api.libs.json.{Format, Json}



case class UserData(userId:Int,id: Int,title: String,body: String)

object UserData {

  implicit val format: Format[UserData] = Json.format[UserData]
}
trait ExternalServiceTrait extends Service {
  def getUser: ServiceCall[NotUsed, UserData]

  override final def descriptor: Descriptor = {
    import Service._
    named("weather")
      .withCalls(
        pathCall("/posts/1", getUser _)
      ).withAutoAcl(true)
  }

}
