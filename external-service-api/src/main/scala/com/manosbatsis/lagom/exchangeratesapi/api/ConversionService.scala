package com.manosbatsis.lagom.exchangeratesapi.api

import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import play.api.libs.json.{Format, Json}

/**
  * An exchange conversion service. Delegates rate retrieval to the [[ExternalRatesService]]
  * @see [[ExternalRatesService]]
  */
trait ConversionService extends Service {

  /**
    * Accepts HTTP POST currency exchange requests to /api/convert
    * with a [[ConversionRequest]] body. Returns a response like the following sample:
    *
    * {{{
    * {
    *    "exchange" : 1.11,
    *    "amount" : 113.886,
    *    "original" : 102.6
    * }
    * }}}
    */
  def convert: ServiceCall[ConversionRequest, ConversionResponse]

  override def descriptor: Descriptor = {
    import Service._
    named("countries-service")
      .withCalls(
        restCall(Method.POST, "/api/convert", convert _)

      )
      .withAutoAcl(true)
  }
}

/**
  * De/serialises from/to the following JSON. Used as input to [[ConversionService#convert()]]
  * {{{
  *
  * {
  *    "fromCurrency": "GBP",
  *    "toCurrency" : "EUR",
  *    "amount" : 102.6
  * }
  * }}}
  * @param fromCurrency
  * @param toCurrency
  * @param amount
  */
case class ConversionRequest(fromCurrency: String, toCurrency: String, amount: BigDecimal)

object ConversionRequest {
  /**
    * Format for converting greeting messages to and from JSON.
    *
    * This will be picked up by a Lagom implicit conversion from Play's JSON format to Lagom's message serializer.
    */
  implicit val format: Format[ConversionRequest] = Json.format[ConversionRequest]
}

/**
  * De/serialises from/to the following JSON. Used as output of [[ConversionService#convert()]]
  * {{{
  *
  * {
  *    "exchange" : 1.11,
  *    "amount" : 113.886,
  *    "original" : 102.6
  * }
  * }}}
  * @param exchange
  * @param amount
  * @param original
  */
case class ConversionResponse(exchange: BigDecimal, amount: BigDecimal, original: BigDecimal)

object ConversionResponse {
  /**
    * Format for converting greeting messages to and from JSON.
    *
    * This will be picked up by a Lagom implicit conversion from Play's JSON format to Lagom's message serializer.
    */
  implicit val format: Format[ConversionResponse] = Json.format[ConversionResponse]
}