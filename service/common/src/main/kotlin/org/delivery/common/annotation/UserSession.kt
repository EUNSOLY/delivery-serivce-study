package org.delivery.common.annotation

import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Service


@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Service
annotation class UserSession(

    @get:AliasFor(annotation = Service::class)
    // 초기값
    val value: String = ""
)
