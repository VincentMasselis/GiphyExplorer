package com.vincentmasselis.giphyexplorer.endpoint.impl

import com.beust.klaxon.JsonObject
import com.vincentmasselis.giphyexplorer.domain.Animation
import com.vincentmasselis.giphyexplorer.endpoint.SearchEndpoint
import com.vincentmasselis.giphyexplorer.endpoint.impl.parser.AnimationParser
import io.reactivex.rxjava3.core.Single
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Provider

class SearchEndpointImpl @Inject constructor(
    private val client: OkHttpClient,
    private val giphyUrl: Provider<HttpUrl.Builder>,
    private val parser: AnimationParser
) : SearchEndpoint {
    override fun search(text: String): Single<List<Animation>> = Request.Builder()
        .url(giphyUrl.get().addPathSegment("search").addQueryParameter("q", text).build())
        .get()
        .build()
        .let { client.newCall(it) }
        .toSingle()
        .toJson<JsonObject>()
        .map { jsonObject ->
            jsonObject
                .array<JsonObject>("data")!!
                .mapNotNull { parser.parse(it) } // If the parsing fails, the value is ignored
        }
}