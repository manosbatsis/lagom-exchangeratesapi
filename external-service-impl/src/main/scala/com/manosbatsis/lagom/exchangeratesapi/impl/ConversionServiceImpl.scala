package com.manosbatsis.lagom.exchangeratesapi.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.manosbatsis.lagom.exchangeratesapi.api.{ConversionRequest, ConversionResponse, ConversionService, ExternalRatesService}
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * [[ConversionService]] implementation.
  * @inheritdoc
  */
class ConversionServiceImpl(externalService: ExternalRatesService) extends ConversionService {

  private final val log: Logger = LoggerFactory.getLogger(classOf[ConversionServiceImpl])

  /**
    * @inheritdoc
    */
  override def convert: ServiceCall[ConversionRequest, ConversionResponse] = ServiceCall {
    request =>
      val result = externalService.getRates(request.fromCurrency, request.toCurrency).invoke()
      result.map { response =>
        val rate: BigDecimal = response.rates(request.toCurrency)
        ConversionResponse(
          exchange = rate,
          amount = rate * request.amount,
          original = request.amount)
      }
  }
}