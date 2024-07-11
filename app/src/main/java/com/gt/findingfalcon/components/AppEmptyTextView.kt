package com.gt.findingfalcon.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.gt.findingfalcon.utilities.LocalSpacing

@Composable
fun AppEmptyTextView(
    emptyText: String,
) {
    val localSpacing = LocalSpacing.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(localSpacing.spaceMedium),
        contentAlignment = Alignment.Center
    ) {
        AppTextField(
            text = emptyText,
            fontSize = localSpacing.textMedium,
            textAlign = TextAlign.Center
        )
    }

}