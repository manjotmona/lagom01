package com.knoldus

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader}
import com.softwaremill.macwire.wire
import play.api.libs.ws.ahc.AhcWSComponents

class HellolagomLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new HellolagomApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new HellolagomApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[HelloLagomService])
 // lazy val externalService = serviceClient.implement[ExternalServiceTrait]
}

abstract class HellolagomApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
  //  with CassandraPersistenceComponents
  //  with LagomKafkaComponents
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[HelloLagomService](wire[HelloLagomServiceImp])
  lazy val externalService = serviceClient.implement[ExternalServiceTrait]

  // Register the JSON serializer registry
 // override lazy val jsonSerializerRegistry = HellolagomSerializerRegistry

  // Register the hello-lagom persistent entity
 // persistentEntityRegistry.register(wire[HellolagomEntity])
}

