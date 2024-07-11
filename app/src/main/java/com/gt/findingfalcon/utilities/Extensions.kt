package com.gt.findingfalcon.utilities

import android.content.Context
import android.widget.Toast
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult

// Toast extensions
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

// SnackBar With Action
suspend fun ScaffoldState.showSnackBar(
    message: String, actionLabel: String? = null,
    actionClicked: () -> Unit = {},
    snackBarDismissed: () -> Unit = {}
) {
    val snackBarResult: SnackbarResult = this.snackbarHostState.showSnackbar(
        message = message,
        actionLabel = actionLabel,
        duration = SnackbarDuration.Short
    )
    when (snackBarResult) {
        SnackbarResult.ActionPerformed -> {
            actionClicked
        }
        SnackbarResult.Dismissed -> {
            snackBarDismissed
        }
    }
}

// Validate String Extension
fun String?.isValidString(): Boolean =
    this != null && !this.equals("null", true)
            && this.trim().isNotEmpty()

fun Int?.isValidInt(): Boolean = (this != null) && (this > 0)