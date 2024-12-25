package com.times.foucse_i.util

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import com.times.foucse_i.R

object SoundUtil {
    private const val TAG = "SoundUtil"
    private var mediaPlayer: MediaPlayer? = null

    fun playTimerCompleteSound(context: Context) {
        try {
            // Release any existing MediaPlayer instance
            release()
            
            // Create and configure MediaPlayer
            mediaPlayer = MediaPlayer.create(context, R.raw.timer_complete)?.apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .build()
                )
                setVolume(1.0f, 1.0f)
                setOnCompletionListener {
                    release()
                }
                start()
            }
            
            if (mediaPlayer == null) {
                Log.e(TAG, "Failed to create MediaPlayer")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error playing sound", e)
            release()
        }
    }

    fun release() {
        try {
            mediaPlayer?.apply {
                if (isPlaying) {
                    stop()
                }
                release()
            }
            mediaPlayer = null
        } catch (e: Exception) {
            Log.e(TAG, "Error releasing MediaPlayer", e)
        }
    }
} 