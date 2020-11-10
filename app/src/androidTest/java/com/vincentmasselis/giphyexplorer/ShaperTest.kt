package com.vincentmasselis.giphyexplorer

import android.util.Size
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.vincentmasselis.giphyexplorer.ioc.DaggerEndpointComponent
import com.vincentmasselis.giphyexplorer.ioc.DaggerRootComponent
import com.vincentmasselis.giphyexplorer.ui.utils.rxLayoutChangeListener
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.Singles
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class ShaperTest {

    private val rootComponent = DaggerRootComponent.factory().build(DaggerEndpointComponent.factory().build())

    /** Compare 2 random animations and check that they are different */
    @Test
    fun random() {
        Singles
            .zip(
                rootComponent.randomAnimationShaper.animation(),
                rootComponent.randomAnimationShaper.animation()
            )
            .test()
            .awaitDone(30, TimeUnit.SECONDS)
            .assertValue { (first, second) -> first != second }
    }

    /** Search for a gif list and checks the returned list is not empty */
    @Test
    fun search() {
        rootComponent.searchFactory.build(Observable.just("coucou")).searchAnimationShaper.list()
            .test()
            .awaitDone(30, TimeUnit.SECONDS)
            .assertValue { it.isNotEmpty() }
    }

    @get:Rule
    val activityRule = ActivityTestRule(ImageViewFrameDebugActivity::class.java, true, false)

    /**
     *  Open 2 different activities, the first holds a small ImageView and the second holds a wide ImageView and checks the returned url by
     *  the shaper is different regarding to the size of the current ImageView. For a wide ImageView, the animation url should use a bigger
     *  animation, for a small ImageView, we should use a thin animation.
     */
    @Test
    fun frameAdaptedUrl() {
        val animationToUse = rootComponent.randomAnimationShaper.animation().blockingGet()
        val smallUrl = activityRule.launchActivity(ImageViewFrameDebugActivity.intent(applicationContext, 150, 150)).let { activity ->
            rootComponent.animationFactory.build(
                animationToUse,
                activity.imageView.rxLayoutChangeListener().map { Size(it.right - it.left, it.bottom - it.top) }
            )
                .frameAdaptedUrlShaper.url()
                .takeUntil(Observable.timer(1, TimeUnit.SECONDS))
                .blockingLast()
        }
        activityRule.finishActivity()
        val bigUrl = activityRule.launchActivity(ImageViewFrameDebugActivity.intent(applicationContext, 800, 800)).let { activity ->
            rootComponent.animationFactory.build(
                animationToUse,
                activity.imageView.rxLayoutChangeListener().map { Size(it.right - it.left, it.bottom - it.top) }
            )
                .frameAdaptedUrlShaper.url()
                .takeUntil(Observable.timer(1, TimeUnit.SECONDS))
                .blockingLast()
        }
        assert(smallUrl.first != bigUrl.first && smallUrl.second != bigUrl.second)
    }
}