package edu.knoldus

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}

trait UserService extends Service {
  def testExternal: ServiceCall[NotUsed,String]

  override final def descriptor = {
    import Service._
    // @formatter:off
    named("external-lagom")
      .withCalls(
        pathCall("/externalLagom/get",testExternal _)
      )
      .withAutoAcl(true)
    // @formatter:on
  }
}
