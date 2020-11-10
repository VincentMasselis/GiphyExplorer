package com.vincentmasselis.giphyexplorer.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.vincentmasselis.giphyexplorer.R
import com.vincentmasselis.giphyexplorer.databinding.ActivityAnimationBinding
import com.vincentmasselis.giphyexplorer.domain.Animation
import com.vincentmasselis.giphyexplorer.ui.utils.viewBinding
import com.vincentmasselis.rxuikotlin.disposeOnState
import com.vincentmasselis.rxuikotlin.utils.ActivityState
import io.reactivex.rxjava3.core.Single
import kotlin.contracts.ExperimentalContracts

/** Displays an [Animation] in full screen */
class AnimationActivity : AppCompatActivity(R.layout.activity_animation) {

    companion object {
        private const val PARAM_ANIMATION = "PARAM_ANIMATION"
        fun intent(context: Context, animation: Animation) = Intent(context, AnimationActivity::class.java).apply {
            putExtra(PARAM_ANIMATION, animation)
        }
    }

    private val binding by viewBinding(ActivityAnimationBinding::bind)

    @ExperimentalContracts
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Single
            .fromCallable {
                (supportFragmentManager.findFragmentById(R.id.activity_animation_container) as AnimationViewerFragment?)
                    ?: AnimationViewerFragment.builder(intent.getParcelableExtra(PARAM_ANIMATION)!!).also {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.activity_animation_container, it)
                            .commitNow()
                    }
            }
            .flatMapObservable { it.animationDisplayedObs }
            .subscribe { animationDisplayed ->
                if (animationDisplayed) {
                    binding.activityAnimationLoadingView.cancelAnimation()
                    binding.activityAnimationLoadingView.visibility = View.GONE
                    binding.activityAnimationLoadingBackground.visibility = View.GONE
                } else {
                    binding.activityAnimationLoadingView.playAnimation()
                    binding.activityAnimationLoadingView.visibility = View.VISIBLE
                    binding.activityAnimationLoadingBackground.visibility = View.VISIBLE
                }
            }
            .disposeOnState(ActivityState.DESTROY, this)
    }
}