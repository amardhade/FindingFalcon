package com.gt.findingfalcon.dto

class ResponseWrapper<T, ApiStatus, AppException>(
    var response: T? = null,
    var apiStatus: ApiStatus? = null,
    var exception: AppException? = null

)