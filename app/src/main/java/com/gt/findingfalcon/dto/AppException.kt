package com.gt.findingfalcon.dto

import android.content.Context
import com.gt.findingfalcon.utilities.UiText


sealed class AppException {
    data class ExceptionWithMessage(val message: String?) : AppException()
    data class ExceptionWithMessageCode(val messageCode: Int) : AppException()

    fun errorMessage(context: Context): String {
        return when (this) {
            is ExceptionWithMessage -> message ?: ""
            else -> ""
        }
    }
}
