package com.vincentmasselis.giphyexplorer.shaper.impl

import com.vincentmasselis.giphyexplorer.domain.Animation
import com.vincentmasselis.giphyexplorer.endpoint.RandomEndpoint
import com.vincentmasselis.giphyexplorer.ioc.SingleInstance
import com.vincentmasselis.giphyexplorer.shaper.RandomAnimationShaper
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@SingleInstance
class RandomAnimationShaperImpl @Inject constructor(
    private val endpoint: RandomEndpoint
) : RandomAnimationShaper {
    override fun animation(): Single<Animation> = endpoint.randomAnimation()
}