package com.vesam.barexamlibrary.utils.extention

import android.app.Activity
import android.media.MediaPlayer
import android.widget.TextView
import java.util.*


private fun initTimerSound(textView: TextView,mediaPlayer: MediaPlayer,activity: Activity) {
    val timer = Timer()
    timer.schedule(object : TimerTask() {
        var updateUI =
            Runnable {
                textView.text =
                    initConvertMillieToHMmSs(mediaPlayer.currentPosition.toLong())
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

        override fun run() = activity.runOnUiThread(updateUI)
    }, 0, 50)
}

fun initConvertMillieToHMmSs(millie: Long): String {
    val seconds = millie / 1000
    val second = seconds % 60
    val minute = seconds / 60 % 60
    val hour = seconds / (60 * 60) % 24
    return when {
        hour > 0 -> String.format("%02d:%02d:%02d", hour, minute, second)
        else -> String.format("%02d:%02d", minute, second)
    }
}