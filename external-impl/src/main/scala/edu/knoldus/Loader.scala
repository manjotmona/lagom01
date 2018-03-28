package edu.knoldus

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader}
import com.softwaremill.macwire.wire
import play.api.libs.ws.ahc.AhcWSComponents



class Loader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new HellolagomApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new HellolagomApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[UserService])
  // lazy val externalService = serviceClient.implement[ExternalServiceTrait]
}

abstract class HellolagomApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    //  with CassandraPersistenceComponents
    //  with LagomKafkaComponents
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[UserService](wire[ServiceImpl])
  lazy val externalService = serviceClient.implement[ExternalService]

  // Register the JSON serializer registry
  // override lazy val jsonSerializerRegistry = HellolagomSerializerRegistry

  // Register the hello-lagom persistent entity
  // persistentEntityRegistry.register(wire[HellolagomEntity])
}
