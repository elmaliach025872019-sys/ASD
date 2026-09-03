package com.example.assistant.utils

import android.content.Context
import android.media.AudioManager
import android.view.KeyEvent

class MediaControlManager(private val context: Context) {

    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    fun togglePlayPause() {
        sendMediaKeyEvent(KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE)
    }

    fun nextTrack() {
        sendMediaKeyEvent(KeyEvent.KEYCODE_MEDIA_NEXT)
    }

    fun previousTrack() {
        sendMediaKeyEvent(KeyEvent.KEYCODE_MEDIA_PREVIOUS)
    }

    private fun sendMediaKeyEvent(keyCode: Int) {
        val downEvent = KeyEvent(KeyEvent.ACTION_DOWN, keyCode)
        val upEvent = KeyEvent(KeyEvent.ACTION_UP, keyCode)
        audioManager.dispatchMediaKeyEvent(downEvent)
        audioManager.dispatchMediaKeyEvent(upEvent)
    }
}
