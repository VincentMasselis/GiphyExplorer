package com.vincentmasselis.giphyexplorer.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/** Data on a version of this GIF downsized to be under 200kb. */
@Parcelize
data class DownsizedSmall(
    override val mp4Url: String,
    override val mp4Size: Long,
    override val width: Int,
    override val height: Int
) : Parcelable, Mp4