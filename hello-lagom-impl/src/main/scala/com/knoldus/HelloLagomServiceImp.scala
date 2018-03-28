package com.knoldus

import scala.collection.mutable.ListBuffer
import scala.concurrent.{ExecutionContext, Future}

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.broker.TopicProducer
import com.lightbend.lagom.scaladsl.persistence.{EventStreamElement, PersistentEntityRegistry}


class HelloLagomServiceImp(externalService: ExternalServiceTrait)(implicit ec: ExecutionContext) extends HelloLagomService {

val emp1 = Employee("Rahul",20)
  val emp2 = Employee("Aakash",24)

  var empList = new ListBuffer[Employee]()
  empList += emp1
  empList += emp2

  override def getEmployee(name: String) =  ServiceCall{ _ =>
   // empList foreach()
   val emp =  empList.filter(emp => emp.name == name)
    Future.successful(emp.toList)

  }
  override def deleteEmployee(name: String) =  ServiceCall{ _ =>

   // val record = Employee(emp.name,emp.age)
   val delete =  empList.filter(emp => emp.name == name)
   // Done
    empList --= delete
    Future.successful(Done)
  }

  override def testUser(id: Int) = ServiceCall { _ =>
    val result: Future[UserData] = externalService.getUser.invoke()
    result.map(response => response)
  }

  override def addEmployee() =  ServiceCall{ emp =>

    val record = Employee(emp.name,emp.age)
    empList += record
    Done
    Future.successful(Done)
  }
  override def updateEmployee() =  ServiceCall{ emp =>

    val record = Employee(emp.name,emp.age)
    if(empList.exists(e => e== record)){

      //empList --= delete
      empList += record
    }

    Done
    Future.successful("Done")
  }

  override def hello(id: String) = ServiceCall { _ =>
    // Look up the hello-lagom entity for the given ID.
   // val ref = persistentEntityRegistry.refFor[HellolagomEntity](id)

    // Ask the entity the Hello command.
   // ref.ask(Hello(id))
    Future.successful(id)

  }


  override def print(id: String) = ServiceCall { request =>
    // Look up the hello-lagom entity for the given ID.
    // val ref = persistentEntityRegistry.refFor[HellolagomEntity](id)

    // Tell the entity to use the greeting message specified.
    // ref.ask(UseGreetingMessage(request.message))
    Future.successful(id)
  }


}

