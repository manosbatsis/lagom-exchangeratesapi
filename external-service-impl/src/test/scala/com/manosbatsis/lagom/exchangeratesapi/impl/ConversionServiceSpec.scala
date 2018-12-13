package com.github.manosbatsis.lagomcurrencyexchange.impl

import com.lightbend.lagom.scaladsl.server.LocalServiceLocator
import com.lightbend.lagom.scaladsl.testkit.ServiceTest
import com.manosbatsis.lagom.exchangeratesapi.api.ConversionService
import com.manosbatsis.lagom.exchangeratesapi.impl.ConversionServiceApplication
import org.scalatest.{AsyncWordSpec, BeforeAndAfterAll, Matchers}

class ConversionServiceSpec extends AsyncWordSpec with Matchers with BeforeAndAfterAll {

  private val server = ServiceTest.startServer(
    ServiceTest.defaultSetup
      .withCassandra()
  ) { ctx =>
    new ConversionServiceApplication(ctx) with LocalServiceLocator
  }

  val client = server.serviceClient.implement[ConversionService]

  override protected def afterAll() = server.stop()

  "lagom-currency-exchange service" should {

    // TODO: workaround to lagom issue?
    // https://github.com/lagom/lagom/issues/322
//    "allow responding with a custom message" in {
//      for {
//        answer <- client.convert.invoke(ConversionRequest("GBP", "EUR", 102.6))
//      } yield {
//        answer.original should ===(102.6)
//      }
//    }
  }

}