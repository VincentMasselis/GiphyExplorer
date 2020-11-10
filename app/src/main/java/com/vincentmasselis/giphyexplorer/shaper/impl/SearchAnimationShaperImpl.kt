package com.vincentmasselis.giphyexplorer.shaper.impl

import com.vincentmasselis.giphyexplorer.domain.Animation
import com.vincentmasselis.giphyexplorer.endpoint.SearchEndpoint
import com.vincentmasselis.giphyexplorer.shaper.SearchAnimationShaper
import dagger.Reusable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Reusable
class SearchAnimationShaperImpl @Inject constructor(
    inputStringObs: Observable<String>,
    endpoint: SearchEndpoint
) : SearchAnimationShaper {

    // Event with multiples subscriptions to [List()], only a single request is made to the endpoint
    private val inputStringObs = inputStringObs
        .debounce(600, TimeUnit.MILLISECONDS)
        .switchMapSingle {
            if (it.isNotBlank()) endpoint.search(it)
            else Single.never()
        }
        .share()

    override fun list(): Observable<List<Animation>> = inputStringObs.hide()
}