package com.gt.findingfalcon.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gt.findingfalcon.utilities.Orange
import com.gt.findingfalcon.utilities.White

@Composable
fun AppButton(
    modifier: Modifier,
    textLabel: String,
    isEnabled: Boolean = true,
    shape: Shape = RoundedCornerShape(corner = CornerSize(size = 10.dp)),
    fontSize: TextUnit = 18.sp,
    backgroundColor: Color = Orange,
    contentColor: Color = White,
    disabledBackgroundColor: Color = Orange.copy(alpha = 0.5f),
    disabledContentColor: Color = White.copy(alpha = 0.5f),
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() }, modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            disabledBackgroundColor = disabledBackgroundColor,
            disabledContentColor = disabledContentColor
        ),
        enabled = isEnabled, shape = shape, contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = textLabel,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = fontSize,
        )

    }

}