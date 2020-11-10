package com.vincentmasselis.giphyexplorer.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/** Data on the 15 second version of the GIF looping. */
@Parcelize
data class Looping(
    val mp4Url: String
) : Parcelable