package com.vincentmasselis.giphyexplorer.ui.utils

import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.vincentmasselis.giphyexplorer.endpoint.GlideRequest
import io.reactivex.rxjava3.core.Single
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

data class ResourceReady<T>(
    val resource: T,
    val model: Any,
    val target: Target<T>?,
    val dataSource: DataSource?,
    val isFirstResource: Boolean
)

class LoadFailed(val glideException: GlideException?, val model: Any, val target: Target<Any>, val isFirstResource: Boolean) : Throwable()

/** Rx wrapper for [GlideRequest.addListener] */
@ExperimentalContracts
inline fun <T> GlideRequest<T>.rxListener(block: (Single<ResourceReady<T>>) -> Unit): GlideRequest<T> {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    block(Single.create { downStream ->
        addListener(object : RequestListener<T> {
            override fun onResourceReady(
                resource: T,
                model: Any,
                target: Target<T>,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                downStream.onSuccess(ResourceReady(resource, model, target, dataSource, isFirstResource))
                return false
            }

            @Suppress("UNCHECKED_CAST")
            override fun onLoadFailed(e: GlideException?, model: Any, target: Target<T>, isFirstResource: Boolean): Boolean {
                downStream.tryOnError(LoadFailed(e, model, target as Target<Any>, isFirstResource))
                return false
            }
        })

        downStream.setCancellable { listener(null) }
    })
    return this
}