package com.vincentmasselis.giphyexplorer.endpoint.impl

import com.beust.klaxon.JsonBase
import com.beust.klaxon.Parser
import io.reactivex.rxjava3.core.Single
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.internal.http2.StreamResetException
import java.io.EOFException
import java.io.IOException
import java.io.InterruptedIOException
import java.net.SocketTimeoutException

/**
 * Used to identify an exception when requesting a http link by using Http.
 *
 * Because of the multiple possibilities of failure when chaining a dozen of Observables, finding exactly where the exception was fired is
 * not so easy. To easily recognize http exceptions from other exception, every exception fired by OkHTTP is wrapped into [HttpIOException]
 */
data class HttpIOException(val source: Throwable) : Throwable(source) {
    override fun toString(): String = "HttpIOException(source=$source)"
}

/**
 * Unlike [HttpIOException], [HttpCodeException] is a computed exception, that's mean the request were successfully executed en returned by
 * OkHTTP but the server refused to give a response and returned an error code.
 */
data class HttpCodeException(val response: Response) : RuntimeException() {
    override fun toString(): String = "HttpCodeException(response=$response)"
}

internal fun Call.toSingle() = Single.create<Response> { downStream ->
    downStream.setCancellable { cancel() }
    enqueue(object : Callback {
        override fun onResponse(call: Call, response: Response) {
            if (response.code in 200..399) downStream.onSuccess(response)
            else downStream.tryOnError(HttpCodeException(response))
        }

        override fun onFailure(call: Call, e: IOException) {
            // When a request is canceled an IOException is fire. In this case, I don't fallback the Exception to the downStream.
            if (call.isCanceled()) return

            downStream.tryOnError(HttpIOException(e))
        }
    })
}

internal inline fun <reified E : JsonBase> Single<Response>.toJson(): Single<E> = map { response -> response.toJson<E>() }

internal inline fun <reified E : JsonBase> Response.toJson(): E = body
    .let { it ?: throw IllegalStateException("My body is not ready, it is empty.") }
    .let { Parser.default().parse(it) }

internal inline fun <reified E> Parser.parse(responseBody: ResponseBody): E =
    try {
        parse(responseBody.byteStream(), responseBody.contentType()?.charset() ?: Charsets.UTF_8) as E
    } catch (e: Throwable) {
        // HTTP/2 could returns a kind of "multi-part" response body which acts like a stream written by the server. That means a http
        // request could be successful (200 response code for example) but an error could occur while reading the response stream because
        // the internet connection could shut down during this moment.
        when (e) {
            is EOFException -> throw HttpIOException(e)
            is InterruptedIOException -> throw HttpIOException(e)
            is SocketTimeoutException -> throw HttpIOException(e)
            is StreamResetException -> throw HttpIOException(e)
            else -> throw e
        }
    }