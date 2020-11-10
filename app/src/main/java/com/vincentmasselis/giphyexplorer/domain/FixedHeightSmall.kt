package com.vincentmasselis.giphyexplorer.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/** Data on versions of this GIF with a fixed height of 100 pixels. Good for mobile keyboards. */
@Parcelize
data class FixedHeightSmall(
    override val gifUrl: String,
    override val width: Int,
    override val height: Int,
    override val gifSize: Long,
    override val mp4Url: String,
    override val mp4Size: Long,
    override val webPUrl: String,
    override val wepPSize: Long
) : Parcelable, Gif, Mp4, WebP