package com.vincentmasselis.giphyexplorer.ui

import android.os.Bundle
import android.util.Size
import android.view.View
import androidx.fragment.app.Fragment
import com.vincentmasselis.giphyexplorer.R
import com.vincentmasselis.giphyexplorer.databinding.FragmentAnimationViewerBinding
import com.vincentmasselis.giphyexplorer.domain.Animation
import com.vincentmasselis.giphyexplorer.endpoint.GlideApp
import com.vincentmasselis.giphyexplorer.rootComponent
import com.vincentmasselis.giphyexplorer.shaper.FrameAdaptedUrlShaper
import com.vincentmasselis.giphyexplorer.ui.utils.rxLayoutChangeListener
import com.vincentmasselis.giphyexplorer.ui.utils.rxListener
import com.vincentmasselis.giphyexplorer.ui.utils.updateConstraints
import com.vincentmasselis.giphyexplorer.ui.utils.viewBinding
import com.vincentmasselis.rxuikotlin.disposeOnState
import com.vincentmasselis.rxuikotlin.utils.FragmentState
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject
import kotlin.contracts.ExperimentalContracts

/** Fragment to display an [Animation], until the animation is loaded, this fragment is transparent */
class AnimationViewerFragment : Fragment(R.layout.fragment_animation_viewer) {

    companion object {
        private const val PARAM_ANIMATION = "PARAM_ANIMATION"
        fun builder(animation: Animation) = AnimationViewerFragment().apply {
            arguments = Bundle().apply { putParcelable(PARAM_ANIMATION, animation) }
        }
    }

    private val animationDisplayedSubject = BehaviorSubject.createDefault(false)
    val animationDisplayedObs = animationDisplayedSubject.distinctUntilChanged()!!

    @Inject lateinit var frameAdaptedUrlShaper: FrameAdaptedUrlShaper
    @Inject lateinit var animation: Animation

    private val binding by viewBinding(FragmentAnimationViewerBinding::bind)

    @ExperimentalContracts
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rootComponent.animationFactory.build(
            requireArguments().getParcelable(PARAM_ANIMATION)!!,
            binding.fragmentGifViewerImageView.rxLayoutChangeListener()
                .map { Size(it.right - it.left, it.bottom - it.top) }
        ).also { it.inject(this) }

        binding.fragmentAnimationViewerConstraintLayout.updateConstraints {
            setDimensionRatio(
                R.id.fragment_gif_viewer_image_view,
                "${animation.images.fixedWidth.width}:${animation.images.fixedWidth.height}"
            )
        }

        frameAdaptedUrlShaper.url()
            .subscribe { (thumbnailUrl, imageUrl) ->
                GlideApp.with(this)
                    .load(imageUrl)
                    .thumbnail(
                        GlideApp.with(this)
                            .load(thumbnailUrl)
                            .rxListener {
                                it.subscribe(
                                    { animationDisplayedSubject.onNext(true) },
                                    { }
                                ).disposeOnState(FragmentState.DESTROY_VIEW, this)
                            }
                    )
                    .rxListener {
                        it.subscribe(
                            { animationDisplayedSubject.onNext(true) },
                            { animationDisplayedSubject.onNext(false) }
                        ).disposeOnState(FragmentState.DESTROY_VIEW, this)
                    }
                    .into(binding.fragmentGifViewerImageView)
            }
            .disposeOnState(FragmentState.DESTROY_VIEW, this)


    }
}