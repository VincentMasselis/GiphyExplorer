package com.vincentmasselis.giphyexplorer.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/** Data on a version of this GIF downsized to be under 8mb. */
@Parcelize
data class DownsizedLarge(
    override val gifUrl: String,
    override val gifSize: Long,
    override val width: Int,
    override val height: Int
) : Parcelable, Gif