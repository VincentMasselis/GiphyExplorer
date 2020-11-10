package com.vincentmasselis.giphyexplorer.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/** Data on a static preview image of the original GIF. */
@Parcelize
data class OriginalStill(
    override val gifUrl: String,
    override val width: Int,
    override val height: Int
) : Parcelable, GifStill