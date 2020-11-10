package com.vincentmasselis.giphyexplorer.ioc

import com.vincentmasselis.giphyexplorer.shaper.SearchAnimationShaper
import com.vincentmasselis.giphyexplorer.shaper.impl.SearchAnimationShaperImpl
import dagger.Binds
import dagger.Module

@Module
interface SearchModule {
    @Binds
    fun searchAnimationShaper(searchAnimationShaperImpl: SearchAnimationShaperImpl): SearchAnimationShaper
}