package com.vincentmasselis.giphyexplorer.ui

import android.util.Size
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Priority
import com.jakewharton.rxbinding4.view.clicks
import com.vincentmasselis.giphyexplorer.R
import com.vincentmasselis.giphyexplorer.databinding.CellAnimationBinding
import com.vincentmasselis.giphyexplorer.domain.Animation
import com.vincentmasselis.giphyexplorer.endpoint.GlideApp
import com.vincentmasselis.giphyexplorer.rootComponent
import com.vincentmasselis.giphyexplorer.shaper.FrameAdaptedUrlShaper
import com.vincentmasselis.giphyexplorer.ui.utils.rxLayoutChangeListener
import com.vincentmasselis.giphyexplorer.ui.utils.updateConstraints
import com.vincentmasselis.rxuikotlin.LifecycleViewHolder
import com.vincentmasselis.rxuikotlin.disposeOnState
import com.vincentmasselis.rxuikotlin.utils.ViewHolderState
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * Displays an animation into a cell, intended to be used in a vertical scrolling recycler view on since the height is wrap_content but the
 * width uses `match_parent`
 */
class AnimationCell(parent: ViewGroup) :
    LifecycleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_animation, parent, false)) {

    private val binding by lazy { CellAnimationBinding.bind(itemView) }
    private val animationSubject = BehaviorSubject.create<Animation>()

    @Inject lateinit var frameAdaptedUrlShaper: FrameAdaptedUrlShaper

    override fun onAdapterAttach() {
        super.onAdapterAttach()
        animationSubject
            .observeOn(Schedulers.computation())
            // Creating a new component is heavy, I run it in a background thread to not slowing down the UI
            .switchMapSingle {
                Single.fromCallable {
                    rootComponent.animationFactory.build(
                        it,
                        binding.cellAnimationContainer.rxLayoutChangeListener().map { Size(it.right - it.left, it.bottom - it.top) }
                    )
                }
            }
            .map { it.inject(this) }
            .switchMap { frameAdaptedUrlShaper.url() }
            .subscribe { (thumbnailUrl, imageUrl) ->
                GlideApp.with(binding.cellAnimationImageView)
                    .load(imageUrl)
                    .thumbnail(GlideApp.with(binding.cellAnimationImageView).load(thumbnailUrl).priority(Priority.HIGH))
                    .into(binding.cellAnimationImageView)
            }
            .disposeOnState(ViewHolderState.ADAPTER_DETACH, this)

        itemView.clicks()
            .withLatestFrom(animationSubject) { _, it -> it }
            .subscribe { itemView.context.startActivity(AnimationActivity.intent(itemView.context, it)) }
            .disposeOnState(ViewHolderState.ADAPTER_DETACH, this)
    }

    fun bind(animation: Animation) {
        binding.cellAnimationContainer.updateConstraints {
            setDimensionRatio(R.id.cell_animation_image_view, "${animation.images.fixedWidth.width}:${animation.images.fixedWidth.height}")
        }
        animationSubject.onNext(animation)
        binding.cellAnimationImageView.setImageDrawable(null)
    }
}