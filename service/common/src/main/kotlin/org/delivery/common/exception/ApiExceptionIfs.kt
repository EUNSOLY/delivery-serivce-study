package org.delivery.common.exception

import org.delivery.common.error.ErrorCodeIfs

// 코틀린은 인터페이스에 변수 가질수있음
interface ApiExceptionIfs  {
    val errorCodeIfs: ErrorCodeIfs?
    val errorDescription:String?
}