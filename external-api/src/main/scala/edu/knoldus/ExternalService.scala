package edu.knoldus

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.Service.{named, pathCall}
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}


trait ExternalService extends Service {
  def getUser: ServiceCall[NotUsed, String]

  override final def descriptor: Descriptor = {
    import Service._
    named("weather")
      .withCalls(
        pathCall("/api/hello/manjot", getUser _)
      ).withAutoAcl(true)
  }

}

