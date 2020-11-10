package com.vincentmasselis.giphyexplorer.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/** Data on a version of this GIF in .MP4 format limited to 50kb that displays the first 1-2 seconds of the GIF. */
@Parcelize
data class PreviewMp4(
    override val mp4Url: String,
    override val mp4Size: Long,
    override val width: Int,
    override val height: Int
) : Parcelable, Mp4