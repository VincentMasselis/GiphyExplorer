package com.vincentmasselis.giphyexplorer.ioc

import com.vincentmasselis.giphyexplorer.shaper.RandomAnimationShaper
import com.vincentmasselis.giphyexplorer.shaper.RequestDurationShaper
import com.vincentmasselis.giphyexplorer.shaper.impl.RandomAnimationShaperImpl
import com.vincentmasselis.giphyexplorer.shaper.impl.RequestDurationShaperImpl
import dagger.Binds
import dagger.Module

@Module
interface RootModule {

    @Binds
    fun requestDurationShaper(requestDurationShaperImpl: RequestDurationShaperImpl): RequestDurationShaper

    @Binds
    fun randomAnimationShaper(randomAnimationShaperImpl: RandomAnimationShaperImpl): RandomAnimationShaper
}