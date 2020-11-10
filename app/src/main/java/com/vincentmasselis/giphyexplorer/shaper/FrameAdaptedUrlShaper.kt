package com.vincentmasselis.giphyexplorer.shaper

import io.reactivex.rxjava3.core.Observable

interface FrameAdaptedUrlShaper {
    /**
     * Returns a different url regarding to the size of the current ImageView. For a wide ImageView, the animation url should refer to a
     * bigger animation, for a small ImageView, the url should refer to thin animation.
     *
     * This shaper uses the [com.vincentmasselis.giphyexplorer.ioc.AnimationComponent.Factory.build] second param to determine the current frame
     * where the animation will be displayed.
     *
     * [Pair.first] contains the thumbnail to use, the [Pair.second] contains the gif
     */
    fun url(): Observable<Pair<String, String>>
}