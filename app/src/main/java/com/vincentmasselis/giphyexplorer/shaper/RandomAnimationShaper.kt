package com.vincentmasselis.giphyexplorer.shaper

import com.vincentmasselis.giphyexplorer.domain.Animation
import io.reactivex.rxjava3.core.Single

interface RandomAnimationShaper {
    /** Returns a random [Animation] from the backend */
    fun animation(): Single<Animation>
}