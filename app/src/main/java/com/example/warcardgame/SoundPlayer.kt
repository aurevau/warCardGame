package com.example.warcardgame

import android.content.Context
import android.media.MediaPlayer

object SoundPlayer {
    fun soundEffect(context: Context) {
        val jokerSound = MediaPlayer.create(context, R.raw.evil_laugh)

        jokerSound.setOnCompletionListener {
            it.release()
        }

        jokerSound.start()
    }
}