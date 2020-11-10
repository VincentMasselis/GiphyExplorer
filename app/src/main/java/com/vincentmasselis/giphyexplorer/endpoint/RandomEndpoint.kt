package com.vincentmasselis.giphyexplorer.endpoint

import com.vincentmasselis.giphyexplorer.domain.Animation
import io.reactivex.rxjava3.core.Single

interface RandomEndpoint {
    fun randomAnimation(): Single<Animation>
}