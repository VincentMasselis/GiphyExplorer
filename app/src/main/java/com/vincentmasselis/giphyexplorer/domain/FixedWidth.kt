package com.vincentmasselis.giphyexplorer.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/** Data on versions of this GIF with a fixed width of 200 pixels. Good for mobile use. */
@Parcelize
data class FixedWidth(
    override val gifUrl: String,
    override val width: Int,
    override val height: Int,
    override val gifSize: Long,
    override val mp4Url: String,
    override val mp4Size: Long,
    override val webPUrl: String,
    override val wepPSize: Long
) : Parcelable, Gif, Mp4, WebP