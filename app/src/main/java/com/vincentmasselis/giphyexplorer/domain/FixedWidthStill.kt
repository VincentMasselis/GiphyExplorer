package com.vincentmasselis.giphyexplorer.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/** Data on a static image of this GIF with a fixed width of 200 pixels. */
@Parcelize
data class FixedWidthStill(
    override val gifUrl: String,
    override val width: Int,
    override val height: Int
) : Parcelable, GifStill