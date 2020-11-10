package com.vincentmasselis.giphyexplorer.endpoint

import com.vincentmasselis.giphyexplorer.domain.Animation
import io.reactivex.rxjava3.core.Single

interface SearchEndpoint {
    fun search(text: String): Single<List<Animation>>
}