package edu.knoldus

import scala.concurrent.{ExecutionContext, Future}

import com.lightbend.lagom.scaladsl.api.ServiceCall

class ServiceImpl(externalService: ExternalService)(implicit ec: ExecutionContext) extends UserService {

  override def testExternal = ServiceCall { _ =>
    val result: Future[String] = externalService.getUser.invoke()
    result.map(response => response)
  }

}
