package com.vesam.barexamlibrary.utils.music_manager

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import com.vesam.barexamlibrary.utils.tools.HandelErrorTools
import java.io.IOException

@Suppress("DEPRECATION")
class BeatBox(private var context: Context,private var handelErrorTools: HandelErrorTools) {

    private lateinit var afd: AssetFileDescriptor
    lateinit var assetManager: AssetManager
    var soundsList: ArrayList<Sound> = ArrayList()

    companion object {

        const val ASSET_DIR_SAMPLE_SOUNDS = "sound"
    }

    init {
        loadSounds()
    }


    private fun loadSounds() {
        assetManager = context.assets

        try {
            val soundsName = assetManager.list(ASSET_DIR_SAMPLE_SOUNDS)
            for (i in 0 until ((soundsName!!.size))) {
                val assertPath = ASSET_DIR_SAMPLE_SOUNDS + "/" + soundsName[i]
                val sound = Sound(assertPath)
                soundsList.add(sound)
            }
        } catch (e: IOException) {

        }

    }


    fun play(sound: Sound, mediaPlayer: MediaPlayer, looping: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mediaPlayer.setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
        } else {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        }
        try {
            afd = assetManager.openFd(sound.assetPath)
            mediaPlayer.setDataSource(
                afd.fileDescriptor,
                afd.startOffset,
                afd.length
            )
            mediaPlayer.isLooping = looping
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener { mp -> mp?.start() }

        } catch (e: Exception) {
            handelErrorTools.handelError(e)
        }
    }

    fun release( mediaPlayer: MediaPlayer) {
        mediaPlayer.release()
    }
}