package com.gt.findingfalcon.utilities

import android.content.Context

sealed class UiText {

    data class DynamicText(val text: String) : UiText()

    data class StringResource(var resId: Int) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicText -> text
            is StringResource -> context.getString(resId)
        }
    }
}