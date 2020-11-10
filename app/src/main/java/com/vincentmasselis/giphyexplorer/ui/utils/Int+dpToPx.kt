package com.vincentmasselis.giphyexplorer.ui.utils

import android.content.res.Resources
import android.util.TypedValue

fun Int.pxToDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
fun Float.dpToPx(): Float = this * Resources.getSystem().displayMetrics.density

fun Int.spToPx() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), Resources.getSystem().displayMetrics)