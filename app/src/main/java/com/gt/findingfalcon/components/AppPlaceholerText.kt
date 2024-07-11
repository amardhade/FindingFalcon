package com.gt.findingfalcon.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black

@Composable
fun AppPlaceholderText(text: String, modifier: Modifier) {
    Text(
        text = text, color = Black,
        modifier = modifier
    )
}