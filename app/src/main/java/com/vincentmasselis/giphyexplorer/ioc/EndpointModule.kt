package com.vincentmasselis.giphyexplorer.ioc

import android.util.Log
import com.vincentmasselis.giphyexplorer.endpoint.RandomEndpoint
import com.vincentmasselis.giphyexplorer.endpoint.SearchEndpoint
import com.vincentmasselis.giphyexplorer.endpoint.impl.RandomEndpointImpl
import com.vincentmasselis.giphyexplorer.endpoint.impl.SearchEndpointImpl
import com.vincentmasselis.giphyexplorer.endpoint.impl.parser.AnimationParser
import com.vincentmasselis.giphyexplorer.endpoint.impl.parser.DateParser
import com.vincentmasselis.giphyexplorer.endpoint.impl.parser.ImagesParser
import com.vincentmasselis.giphyexplorer.endpoint.impl.parser.UserParser
import com.vincentmasselis.giphyexplorer.endpoint.impl.parser.impl.AnimationParserImpl
import com.vincentmasselis.giphyexplorer.endpoint.impl.parser.impl.DateParserImpl
import com.vincentmasselis.giphyexplorer.endpoint.impl.parser.impl.ImagesParserImpl
import com.vincentmasselis.giphyexplorer.endpoint.impl.parser.impl.UserParserImpl
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.subjects.PublishSubject
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Response
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.system.measureTimeMillis

@Module
object EndpointModule {

    private const val TAG = "EndpointModule"
    private val onRequestDurationSubject = PublishSubject.create<Long>()

    @Provides
    @Singleton
    fun httpClient() = OkHttpClient.Builder()
        // Gif could be heavy, let's put a 30s timeout instead of 10s
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            Log.d(TAG, "Requesting ${chain.request().url}")
            val response: Response
            val duration = measureTimeMillis { response = chain.proceed(chain.request()) }
            Log.d(TAG, "Response code ${response.code}, request take $duration to proceed, URL called: ${chain.request().url}")
            onRequestDurationSubject.onNext(duration)
            return@addInterceptor response
        }
        .build()

    @Provides
    fun urlBuilder() = HttpUrl.Builder()
        .scheme("https")
        .host("api.giphy.com")
        .addPathSegment("v1")
        .addPathSegment("gifs")
        .addQueryParameter("api_key", "6nJBN7fnbLMQhI2roMskz9DEpD5LdUeh")

    @Provides
    fun onRequestDuration() = onRequestDurationSubject.hide()!!

    @Provides
    fun randomEndpoint(randomEndpointImpl: RandomEndpointImpl): RandomEndpoint = randomEndpointImpl

    @Provides
    fun searchEndpoint(searchEndpointImpl: SearchEndpointImpl): SearchEndpoint = searchEndpointImpl

    @Provides
    fun animationParser(animationParserImpl: AnimationParserImpl): AnimationParser = animationParserImpl

    @Provides
    fun dateParser(dateParserImpl: DateParserImpl): DateParser = dateParserImpl

    @Provides
    fun imagesParser(imagesParserImpl: ImagesParserImpl): ImagesParser = imagesParserImpl

    @Provides
    fun userParser(userParserImpl: UserParserImpl): UserParser = userParserImpl
}