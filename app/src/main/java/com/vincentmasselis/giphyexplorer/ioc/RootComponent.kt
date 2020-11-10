package com.vincentmasselis.giphyexplorer.ioc

import com.vincentmasselis.giphyexplorer.shaper.RandomAnimationShaper
import com.vincentmasselis.giphyexplorer.ui.MainActivity
import dagger.Component

@SingleInstance
@Component(dependencies = [EndpointComponent::class], modules = [RootModule::class])
interface RootComponent {

    @Component.Factory
    interface Factory {
        fun build(endpointComponent: EndpointComponent): RootComponent
    }

    val animationFactory: AnimationComponent.Factory

    val searchFactory: SearchComponent.Factory

    val randomAnimationShaper: RandomAnimationShaper

    fun inject(mainActivity: MainActivity)
}