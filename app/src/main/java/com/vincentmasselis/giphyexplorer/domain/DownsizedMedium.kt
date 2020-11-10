package com.vincentmasselis.giphyexplorer.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/** Data on a version of this GIF downsized to be under 5mb. */
@Parcelize
data class DownsizedMedium(
    override val gifUrl: String,
    override val gifSize: Long,
    override val width: Int,
    override val height: Int
) : Parcelable, Gif