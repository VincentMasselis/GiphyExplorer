package com.vincentmasselis.giphyexplorer.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/** Data on a static image of this GIF with a fixed height of 100 pixels. */
@Parcelize
data class FixedHeightSmallStill(
    override val gifUrl: String,
    override val width: Int,
    override val height: Int
) : Parcelable, GifStill