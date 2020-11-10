package com.vincentmasselis.giphyexplorer.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/** Data on the original version of this GIF. Good for desktop use. */
@Parcelize
data class Original(
    override val width: Int,
    override val height: Int,
    val size: Long,
    val frames: Int,
    override val mp4Url: String,
    override val mp4Size: Long,
    override val webPUrl: String,
    override val wepPSize: Long
) : Parcelable, Mp4, WebP