package com.delivery.apigw.filter

import com.delivery.apigw.common.Log
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.stereotype.Component

/**
 * api의 filter, interceptor랑 비슷
 */
@Component
class ServiceApiPrivateFilter: AbstractGatewayFilterFactory<ServiceApiPrivateFilter.Config>(Config::class.java) {

    companion object: Log

    class Config

    override fun apply(config: Config): GatewayFilter {

        return GatewayFilter { exchange, chain ->
            val uri = exchange.request.uri

            log.info("service api private filter route uri : {}", uri)

            val mono = chain.filter(exchange)

            mono
        }
    }
}