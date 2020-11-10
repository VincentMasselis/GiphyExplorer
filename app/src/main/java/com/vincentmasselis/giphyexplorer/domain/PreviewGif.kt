package com.vincentmasselis.giphyexplorer.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/** Data on a version of this GIF limited to 50kb that displays the first 1-2 seconds of the GIF. */
@Parcelize
data class PreviewGif(
    override val gifUrl: String,
    override val width: Int,
    override val height: Int
) : Parcelable, GifStill