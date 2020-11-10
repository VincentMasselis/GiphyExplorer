package com.vincentmasselis.giphyexplorer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vincentmasselis.giphyexplorer.databinding.FragmentBlankBinding
import com.vincentmasselis.giphyexplorer.ui.utils.viewBinding

@SuppressLint("SetTextI18n")
class ViewBindingFragment1 : Fragment() {

    val binding: FragmentBlankBinding by viewBinding(FragmentBlankBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_blank, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentBlankTextView.text = "Fragment 1 View Created"
    }

    override fun onDestroyView() {
        binding.fragmentBlankTextView.text = "Fragment 1 View Destroyed"
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            binding.fragmentBlankTextView
            throw UnsupportedOperationException("An exception must be fire")
        } catch (e: IllegalStateException) {
            // Excepted exception
        }
    }
}