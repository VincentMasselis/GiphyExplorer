package com.vincentmasselis.giphyexplorer.ioc

import android.util.Size
import com.vincentmasselis.giphyexplorer.domain.Animation
import com.vincentmasselis.giphyexplorer.shaper.FrameAdaptedUrlShaper
import com.vincentmasselis.giphyexplorer.ui.AnimationActivity
import com.vincentmasselis.giphyexplorer.ui.AnimationCell
import com.vincentmasselis.giphyexplorer.ui.AnimationViewerFragment
import dagger.BindsInstance
import dagger.Subcomponent
import io.reactivex.rxjava3.core.Observable

@Subcomponent(modules = [AnimationModule::class])
interface AnimationComponent {

    @Subcomponent.Factory
    interface Factory {
        fun build(@BindsInstance animation: Animation, @BindsInstance containerSizeObs: Observable<Size>): AnimationComponent
    }

    val frameAdaptedUrlShaper: FrameAdaptedUrlShaper

    fun inject(animationViewerFragment: AnimationViewerFragment)

    fun inject(animationViewerFragment: AnimationCell)

    fun inject(animationActivity: AnimationActivity)
}