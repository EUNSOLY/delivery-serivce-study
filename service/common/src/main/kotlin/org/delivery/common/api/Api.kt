package org.delivery.common.api

import jakarta.validation.Valid
import org.delivery.common.error.ErrorCodeIfs

data class Api<T> (
    var result: Result?=null,

    @field:Valid
    var body : T? = null
){

    companion object{

        fun<T>OK(body:T?):Api<T>{
            return Api(
                result = Result.OK(),
                body=body
            )
        }

        fun<T> ERROR (result: Result):Api<T>{
            return Api(
                result = result

            )
        }

        fun<T> ERROR (errorCodeIfs: ErrorCodeIfs,result: Result):Api<T>{
            return Api(
                result = Result.ERROR(errorCodeIfs)

                )
        }

        fun<T> ERROR (
            errorCodeIfs: ErrorCodeIfs,
            throwable: Throwable
        ):Api<T>{
            return Api(
                result = Result.ERROR(errorCodeIfs,throwable)

            )
        }

        fun<T> ERROR (
            errorCodeIfs: ErrorCodeIfs,
            description:String
        ):Api<T>{
            return Api(
                result = Result.ERROR(errorCodeIfs,description)

            )
        }
    }
}