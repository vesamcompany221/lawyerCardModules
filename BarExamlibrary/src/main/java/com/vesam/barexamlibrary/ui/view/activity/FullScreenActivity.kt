package com.vesam.barexamlibrary.ui.view.activity

import android.content.Intent
import android.media.MediaPlayer
import android.media.session.PlaybackState
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.SeekBar
import com.vesam.barexamlibrary.databinding.ActivityFullScreenBinding
import com.vesam.barexamlibrary.utils.base.BaseActivity
import com.vesam.barexamlibrary.utils.build_config.BuildConfig
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.BUNDLE_CURRENT_POSITION
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.BUNDLE_PATH
import com.vesam.barexamlibrary.utils.tools.HandelErrorTools
import org.koin.android.ext.android.inject

class FullScreenActivity : BaseActivity() {

    private lateinit var binding: ActivityFullScreenBinding
    private val handelErrorTools: HandelErrorTools by inject()
    private var currentPosition = 0
    private var path = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFullScreen()
        binding = ActivityFullScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        try {
            initAction()
        } catch (e: Exception) {
            handelErrorTools.handelError(e)
        }
    }

    override fun onResume() {
        super.onResume()
        initResumeVideo()
    }

    override fun onPause() {
        super.onPause()
        initPauseVideo()
    }

    private fun initResumeVideo() {
        when (binding.video.visibility) {
            View.VISIBLE -> initResultResumeVideo()
        }
    }

    private fun initResultResumeVideo() {
        if (binding.video.isPlaying){
            binding.video.resume()
            initShowPlayView()
        }
    }

    private fun initFullScreen() {
        val layoutParam=window.attributes
        layoutParam.flags= WindowManager.LayoutParams.FLAG_FULLSCREEN
        window.attributes=layoutParam
    }

    private fun initAction() {
        getIntentExtra()
        initVideo()
        initOnClick()
        initOnSeekBarChangeListener()
    }

    private fun initOnSeekBarChangeListener() {
        binding.seekBarTime.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) binding.video.seekTo(seekBar!!.progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun initOnClick() {
        binding.imgExitFullScreen.setOnClickListener { initExitFullScreen() }
        binding.imgPlay.setOnClickListener { initPlayVideo() }
        binding.imgPause.setOnClickListener { initPauseVideo() }
    }

    private fun initPlayVideo() {
        initCheckPlay()
        initShowPlayView()
        initResetStartTime()
        initResetSeekBar()
    }

    private fun initShowPlayView() {
        binding.imgPlay.visibility = View.GONE
        binding.lnController.visibility = View.VISIBLE
        binding.imgExitFullScreen.visibility = View.VISIBLE
    }

    private fun initCheckPlay() = when (binding.video.currentPosition) {
        PlaybackState.STATE_PAUSED -> binding.video.resume()
        else -> binding.video.start()
    }

    private fun initPauseVideo() {
        if (binding.video.isPlaying){
            binding.video.pause()
            initShowPauseView()
        }
    }

    private fun initExitFullScreen() {
        val intent = Intent()
        intent.putExtra(BUNDLE_CURRENT_POSITION, binding.video.currentPosition)
        setResult(RESULT_OK,intent)
        finish()
    }

    private fun initVideo() {
        binding.video.setVideoPath(path)
        binding.video.seekTo(currentPosition)
        binding.video.start()
        binding.video.setOnCompletionListener { initOnCompletionListener() }
        binding.video.setOnPreparedListener(this::initOnPreparedListener)
    }

    private fun initOnCompletionListener() {
        initShowPauseView()
    }

    private fun initShowPauseView() {
        binding.imgPlay.visibility = View.VISIBLE
        binding.lnController.visibility = View.GONE
        binding.imgExitFullScreen.visibility = View.GONE
    }

    private fun initOnPreparedListener(it: MediaPlayer?) {
        initResetSeekBar()
        initDurationSeekBar()
        binding.seekBarTime.postDelayed(onEverySecond, BuildConfig.EVERY_SECOND)
        binding.txtEndTime.text = initConvertMillieToHMmSs(it!!.duration.toLong())
    }

    private val onEverySecond: Runnable = object : Runnable {
        override fun run() {
            val currentPosition: Int = binding.video.currentPosition
            binding.seekBarTime.progress = currentPosition
            binding.txtStartTime.text = initConvertMillieToHMmSs(currentPosition.toLong())
            if (binding.video.isPlaying)
                binding.seekBarTime.postDelayed(this, BuildConfig.EVERY_SECOND)
        }
    }

    private fun initConvertMillieToHMmSs(millie: Long): String {
        val seconds = millie / 1000
        val second = seconds % 60
        val minute = seconds / 60 % 60
        val hour = seconds / (60 * 60) % 24
        return when {
            hour > 0 -> String.format("%02d:%02d:%02d", hour, minute, second)
            else -> String.format("%02d:%02d", minute, second)
        }
    }

    private fun initDurationSeekBar() {
        binding.seekBarTime.max = binding.video.duration
    }

    private fun initResetStartTime() {
        binding.txtStartTime.text = BuildConfig.ZERO_TIME
    }

    private fun initResetSeekBar() {
        binding.seekBarTime.progress = 0
    }

    private fun getIntentExtra() {
        path = intent.extras!!.getString(BUNDLE_PATH, "")
        currentPosition = intent.extras!!.getInt(BUNDLE_CURRENT_POSITION, 0)
    }

    override fun onBackPressed() {
        initExitFullScreen()
    }
}