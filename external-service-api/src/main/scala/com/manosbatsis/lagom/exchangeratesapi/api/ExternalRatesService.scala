package com.manosbatsis.lagom.exchangeratesapi.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import play.api.libs.json.{Format, Json}

/**
  * Integrates api.exchangeratesapi.io as an unmanaged API.
  * Requires registration of the API to the ServiceLocator via build.sbt as:
  *
  * {{{
  * lagomUnmanagedServices in ThisBuild := Map("api-exchangeratesapi-io" -> "https://api.exchangeratesapi.io")
  * }}}
  *
  * See https://www.lagomframework.com/documentation/1.4.x/scala/ServiceLocator.html#Communicating-with-external-services
  */
trait ExternalRatesService extends Service {

  /**
    * Performs an HTTP GET to e.g. https://api.exchangeratesapi.io/latest?base=EUR&symbols=GBP,PHP,USD
    * @param base the original currency symbol
    * @param symbols the target currency symbol(s)
    * @return an [[ExternalLatestRatesResponse]] instance
    */
  def getRates(base: String, symbols: String): ServiceCall[NotUsed, ExternalLatestRatesResponse]

  override def descriptor: Descriptor = {
    import Service._
    named("api-exchangeratesapi-io")
      .withCalls(
        restCall(Method.GET,"/latest?base&symbols", getRates _)
      )
      .withAutoAcl(false)
  }
}

/**
  * A response from the api.exchangeratesapi.io/latest API e.g.
  *
  * {{{
  * {
  *    "date":"2018-12-07",
  *    "base":"EUR",
  *    "rates":{"GBP":0.89085,"PHP":60.059,"USD":1.1371}
  * }
  * }}}
  */
case class ExternalLatestRatesResponse(date: String, base: String, rates: Map[String, BigDecimal])

object ExternalLatestRatesResponse {
  /**
    * Format for converting greeting messages to and from JSON.
    *
    * This will be picked up by a Lagom implicit conversion from Play's JSON format to Lagom's message serializer.
    */
  implicit val format: Format[ExternalLatestRatesResponse] = Json.format[ExternalLatestRatesResponse]
}
