package com.dmoratilla30.wakebus

import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager

class alarmPlayer {
    object alarma {
        var mediaPlayer: MediaPlayer? = null
        fun playAudio(c: Context) {
            createMediaPlayer(c)
            mediaPlayer?.let {
                it.isLooping = true
                if (!it.isPlaying) {
                    it.start()
                }
            }
        }
        private fun createMediaPlayer(c: Context) {
            mediaPlayer?.stop()

            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            mediaPlayer = MediaPlayer.create(c, alarmSound)
        }
        fun stopAudio() {
            mediaPlayer?.stop()
        }
    }
}