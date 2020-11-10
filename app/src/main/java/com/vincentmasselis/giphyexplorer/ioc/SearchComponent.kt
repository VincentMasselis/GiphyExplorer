package com.vincentmasselis.giphyexplorer.ioc

import com.vincentmasselis.giphyexplorer.shaper.SearchAnimationShaper
import com.vincentmasselis.giphyexplorer.ui.SearchAnimationFragment
import dagger.BindsInstance
import dagger.Subcomponent
import io.reactivex.rxjava3.core.Observable

@Subcomponent(modules = [SearchModule::class])
interface SearchComponent {

    @Subcomponent.Factory
    interface Factory {
        fun build(@BindsInstance inputStringObs: Observable<String>): SearchComponent
    }

    val searchAnimationShaper: SearchAnimationShaper

    fun inject(searchAnimationFragment: SearchAnimationFragment)
}