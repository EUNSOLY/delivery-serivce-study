package org.delivery.account.domain.token.controller

import org.delivery.account.common.Log
import org.delivery.account.domain.token.business.TokenBusiness
import org.delivery.account.domain.token.controller.model.TokenValidationRequest
import org.delivery.account.domain.token.controller.model.TokenValidationResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 내부서버 - internal
 * 네트워크에서 조절 후 공개되지않는 주소로 접근(ACL)
 */
@RestController
@RequestMapping("/internal-api/token")
class TokenInternalApiController(
    private val tokenBusiness: TokenBusiness
){

    companion object: Log
    @PostMapping("/validation")
    fun tokenValidation(
        @RequestBody
        tokenValidationRequest: TokenValidationRequest?
    ): TokenValidationResponse {
        log.info("koken validation init : {}", tokenValidationRequest)
        return tokenBusiness.tokenValidation(tokenValidationRequest)
    }
}