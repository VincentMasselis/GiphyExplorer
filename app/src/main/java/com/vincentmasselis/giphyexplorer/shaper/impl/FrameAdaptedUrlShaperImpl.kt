package com.vincentmasselis.giphyexplorer.shaper.impl

import android.util.Size
import com.vincentmasselis.giphyexplorer.domain.Animation
import com.vincentmasselis.giphyexplorer.shaper.FrameAdaptedUrlShaper
import com.vincentmasselis.giphyexplorer.shaper.RequestDurationShaper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.max

class FrameAdaptedUrlShaperImpl @Inject constructor(
    private val requestDurationShaper: RequestDurationShaper,
    private val animation: Animation,
    private val sizeObs: Observable<Size>
) : FrameAdaptedUrlShaper {
    override fun url(): Observable<Pair<String, String>> = sizeObs
        // Ignore ImageView which a non computed size yet
        .filter { max(it.height, it.width) > 0 }
        // An ImageView layout could be computed multiple times before being completely rendered, so I debounce to avoid massive
        // computations during the screen creation
        .debounce(100, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
        .withLatestFrom(
            requestDurationShaper.averageDuration()
                .map { Optional.of(it) }
                .startWith(Single.just(Optional.empty())))
        { size, (duration) -> size to duration }
        .map { (size, requestDuration) ->
            when (requestDuration ?: 0.0 /* Let's start with the best quality assuming the request duration is 0 */) {
                in 0.0..3.0 ->
                    when (max(size.height, size.width)) {
                        in 1..600 -> animation.images.fixedHeightSmallStill.gifUrl to animation.images.fixedHeightSmall.webPUrl
                        in 600..1000 -> animation.images.fixedHeightStill.gifUrl to animation.images.fixedHeight.webPUrl
                        in 1000..Int.MAX_VALUE -> animation.images.originalStill.gifUrl to animation.images.original.webPUrl
                        else -> throw IllegalArgumentException("A size value cannot be <0, size :$size")
                    }
                in 3.0..Double.MAX_VALUE -> // Switch to a lower consuming resource if the average request time is > 3 seconds
                    when (max(size.height, size.width)) {
                        in 1..600 -> animation.images.fixedHeightSmallStill.gifUrl to animation.images.fixedHeightDownSampled.webPUrl
                        in 600..Int.MAX_VALUE -> animation.images.fixedHeightSmallStill.gifUrl to animation.images.fixedHeightSmall.webPUrl
                        else -> throw IllegalArgumentException("A size value cannot be <0, size :$size")
                    }
                else -> throw IllegalArgumentException("A duration value cannot be <0, duration :$requestDuration")
            }
        }
}