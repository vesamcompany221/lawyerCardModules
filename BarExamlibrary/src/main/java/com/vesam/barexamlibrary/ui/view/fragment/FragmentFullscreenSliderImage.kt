@file:Suppress("DEPRECATION")

package com.vesam.barexamlibrary.ui.view.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import com.vesam.barexamlibrary.R
import com.vesam.barexamlibrary.databinding.FragmentFullscreenSliderImageBinding
import com.vesam.barexamlibrary.utils.tools.GlideTools
import com.vesam.barexamlibrary.utils.tools.HandelErrorTools
import org.koin.android.ext.android.inject

@Suppress("DEPRECATION")
class FragmentFullscreenSliderImage : DialogFragment(), View.OnKeyListener {

    private lateinit var binding: FragmentFullscreenSliderImageBinding
    private val handelErrorTools: HandelErrorTools by inject()
    private val glideTools: GlideTools by inject()
    private lateinit var image: String

    fun setImage(image: String) {
        this.image = image
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFullscreenSliderImageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            initAction()
        } catch (e: Exception) {
            handelErrorTools.handelError(e)
        }
    }

    private fun initAction() {
        initSetView()
        initOnClick()
        initOnBackPress()
    }

    private fun initSetView() {
        glideTools.displayImageSliderZoom(binding.zoomView, image)
    }

    private fun initOnClick() {
        binding.imgBtnClose.setOnClickListener { dismiss() }
        binding.lnParent.setOnClickListener {  }
    }

    private fun initOnBackPress() {
        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener(this)
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event!!.action == KeyEvent.ACTION_UP) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dismiss()
                return true
            }
        }
        return false
    }
}