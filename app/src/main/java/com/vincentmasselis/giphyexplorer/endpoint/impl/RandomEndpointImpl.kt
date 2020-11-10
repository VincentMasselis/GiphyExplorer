package com.vincentmasselis.giphyexplorer.endpoint.impl

import com.beust.klaxon.JsonObject
import com.vincentmasselis.giphyexplorer.domain.Animation
import com.vincentmasselis.giphyexplorer.endpoint.RandomEndpoint
import com.vincentmasselis.giphyexplorer.endpoint.impl.parser.AnimationParser
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.Flowables
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Provider

class RandomEndpointImpl @Inject constructor(
    private val client: OkHttpClient,
    private val giphyUrl: Provider<HttpUrl.Builder>,
    private val parser: AnimationParser
) : RandomEndpoint {
    override fun randomAnimation(): Single<Animation> = Single
        .defer {
            Request.Builder()
                .url(giphyUrl.get().addPathSegment("random").build())
                .get()
                .build()
                .let { client.newCall(it) }
                .toSingle()
        }
        .toJson<JsonObject>()
        .map { jsonObject -> parser.parse(jsonObject.obj("data")!!)!! }
        // Sometimes the response from the ws doesn't match the documentation, In this case, I retry by asking a new gif again up to 3 times max
        .retryWhen { source ->
            Flowables.zip(source, Flowable.range(1, 4)).flatMapSingle { (exception, retryCount) ->
                if (exception is NullPointerException && retryCount < 4) Single.just(Unit)
                else Single.error(exception)
            }
        }
}