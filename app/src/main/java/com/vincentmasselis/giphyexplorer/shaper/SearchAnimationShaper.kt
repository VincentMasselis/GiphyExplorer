package com.vincentmasselis.giphyexplorer.shaper

import com.vincentmasselis.giphyexplorer.domain.Animation
import io.reactivex.rxjava3.core.Observable

interface SearchAnimationShaper {
    /**
     * Returns a list of [Animation] depending to the text filled when calling
     * [com.vincentmasselis.giphyexplorer.ioc.SearchComponent.Factory.build]
     */
    fun list(): Observable<List<Animation>>
}