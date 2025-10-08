package org.delivery.account.domain.token.service

import org.delivery.account.common.Log
import org.delivery.account.domain.token.ifs.TokenHelperInterface
import org.delivery.account.domain.token.model.TokenDto
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val tokenHelperIfs: TokenHelperInterface
) {
    companion object: Log

    fun issueAccessToken(userId: Long?): TokenDto? {
        return userId?.let {
            val data = mapOf("userId".to(it))
            tokenHelperIfs.issueAccessToken(data)
        }
    }

    fun issueRefreshToken(userId: Long?): TokenDto? {
        requireNotNull(userId)
        val data = mapOf("userId".to(userId))
        return tokenHelperIfs.issueRefreshToken(data)
    }

    fun validationToken(token: String?): Long? {
//        requireNotNull(token)
        /* 방법 1.
        val map = tokenHelperIfs.validationTokenWithThrow(token)

        val userId = map?.get("userId")
        requireNotNull(userId)
        return userId.toString().toLong()*/
        /* 방법 2.
        return tokenHelperIfs.validationTokenWithThrow(token)
            ?.let { map ->
                map["userid"]
            }?.toString()?.toLong()*/

        //방법 3.
       return token?.let { token ->
           tokenHelperIfs.validationTokenWithThrow(token)
       }?.let { map ->
            map["userId"]
        }?.toString()?.toLong()

    }
}