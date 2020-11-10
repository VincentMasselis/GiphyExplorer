package com.vincentmasselis.giphyexplorer.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val avatarUrl: String,
    val bannerUrl: String,
    val profileUrl: String,
    val username: String,
    val displayName: String
) : Parcelable