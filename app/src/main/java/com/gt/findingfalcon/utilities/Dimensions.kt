package com.gt.findingfalcon.utilities


import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Dimensions(
    val Default: Dp = 0.dp,
    val spaceExtraSmall: Dp = 2.dp,
    val spaceSmall: Dp = 4.dp,
    val spaceMedium: Dp = 8.dp,
    val spaceLarge: Dp = 16.dp,
    val spaceExtraLarge: Dp = 32.dp,
    val DP_100: Dp = 100.dp,
    val text_10_SP :TextUnit = 10.sp,
    val textExtraSmall: TextUnit = 12.sp,
    val textSmall: TextUnit = 14.sp,
    val textDefault: TextUnit = 16.sp,
    val textMedium: TextUnit = 18.sp,
    val textLarge: TextUnit = 22.sp,
    val textExtraLarge: TextUnit = 26.sp,
    val topBarHeight: Dp = 56.dp,
    val CircularSizeSmall: Dp = 20.dp

)

val LocalSpacing = compositionLocalOf { Dimensions() }