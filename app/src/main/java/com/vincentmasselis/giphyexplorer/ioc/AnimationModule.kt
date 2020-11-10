package com.vincentmasselis.giphyexplorer.ioc

import com.vincentmasselis.giphyexplorer.shaper.FrameAdaptedUrlShaper
import com.vincentmasselis.giphyexplorer.shaper.impl.FrameAdaptedUrlShaperImpl
import dagger.Binds
import dagger.Module

@Module
interface AnimationModule {
    @Binds
    fun frameAdaptedUrlShaper(frameAdaptedUrlShaperImpl: FrameAdaptedUrlShaperImpl): FrameAdaptedUrlShaper
}