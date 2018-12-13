package com.manosbatsis.lagom.exchangeratesapi.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader}
import com.manosbatsis.lagom.exchangeratesapi.api.{ConversionService, ExternalRatesService}
import com.softwaremill.macwire.wire
import play.api.libs.ws.ahc.AhcWSComponents

class ConversionServiceLoader  extends LagomApplicationLoader{
  override def load(context: LagomApplicationContext): LagomApplication =
    new ConversionServiceApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new ConversionServiceApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[ConversionService])
}

abstract class ConversionServiceApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[ConversionService](wire[ConversionServiceImpl])

  // Pickup the unmanaged service
  lazy val externalService=serviceClient.implement[ExternalRatesService]
}
