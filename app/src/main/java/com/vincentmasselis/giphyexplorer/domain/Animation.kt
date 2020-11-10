package com.vincentmasselis.giphyexplorer.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * a.k.a. gif object, go [https://developers.giphy.com/docs/api/schema/#gif-object]
 */
@Parcelize
data class Animation(
    val type: String,
    val id: String,
    val title: String,
    val slug: String,
    val url: String,
    val shortUrl: String,
    val embedUrl: String,
    val userName: String,
    val source: String,
    val rating: Rating,
    val user: User?,
    val sourceTld: String,
    val sourcePostUrl: String,
    val updatedAt: Date?,
    val createdAt: Date?,
    val importedAt: Date,
    val trendingAt: Date?,
    val images: Images
) : Parcelable