package com.vincentmasselis.giphyexplorer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.vincentmasselis.giphyexplorer.databinding.FragmentBlankBinding
import com.vincentmasselis.giphyexplorer.ui.utils.viewBinding

@SuppressLint("SetTextI18n")
class ViewBindingFragment2 : Fragment(R.layout.fragment_blank) {

    val binding: FragmentBlankBinding by viewBinding(FragmentBlankBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentBlankTextView.text = "Fragment 2 View Created"
    }

    override fun onDestroyView() {
        binding.fragmentBlankTextView.text = "Fragment 2 View Destroyed"
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