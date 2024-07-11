package com.gt.findingfalcon.repo

import com.google.gson.JsonSyntaxException
import com.gt.findingfalcon.components.AppToolbarType
import com.gt.findingfalcon.dto.AppException
import com.gt.findingfalcon.dto.ResponseWrapper
import com.xpressbees.core.wrappers.ApiStatus
import retrofit2.Response

abstract class BaseRepo() {

    suspend fun <T> apiWrapper(api: suspend () -> Response<T>)
            : ResponseWrapper<T, ApiStatus, AppException> {

        val responseWrapper = ResponseWrapper<T, ApiStatus, AppException>()

        try {
            val response = api()
            if (response.isSuccessful) {
                responseWrapper.apiStatus = ApiStatus.Completed
                responseWrapper.response = response.body()
                responseWrapper.exception = null
            } else {
                responseWrapper.apiStatus = ApiStatus.Exception
                responseWrapper.response = null
                responseWrapper.exception =
                    AppException.ExceptionWithMessage("Something went wrong")
            }
        } catch (e: Exception) {
            responseWrapper.response = null
            responseWrapper.apiStatus = ApiStatus.Exception
            responseWrapper.exception = when (e) {
                is JsonSyntaxException ->
                    AppException.ExceptionWithMessage("Json exception")

                is IllegalStateException ->
                    AppException.ExceptionWithMessage("Illegal state exception")

                else ->
                    AppException.ExceptionWithMessage("Something went wrong")
            }
        }
        return responseWrapper
    }

    suspend fun <T> responseParser(
        response: ResponseWrapper<T, ApiStatus, AppException>,
        statusCode: Int
    ): ResponseWrapper<T, ApiStatus, AppException> {
        var parsedResponse = ResponseWrapper<T, ApiStatus, AppException>()
        if (statusCode == 200) parsedResponse = response
        else {
            parsedResponse.response = null
            parsedResponse.exception = AppException.ExceptionWithMessage("Internal server error")
            parsedResponse.apiStatus = ApiStatus.Exception
        }
        return parsedResponse
    }

}