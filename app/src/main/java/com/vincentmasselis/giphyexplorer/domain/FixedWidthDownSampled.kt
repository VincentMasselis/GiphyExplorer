package com.vincentmasselis.giphyexplorer.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/** Data on versions of this GIF with a fixed width of 200 pixels and the number of frames reduced to 6. */
@Parcelize
data class FixedWidthDownSampled(
    override val gifUrl: String,
    override val width: Int,
    override val height: Int,
    override val gifSize: Long,
    override val webPUrl: String,
    override val wepPSize: Long
) : Parcelable, Gif, WebP