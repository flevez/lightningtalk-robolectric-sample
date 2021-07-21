package com.example.lightningtalk

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings
import android.util.Log

class MyCustomService : Service() {

    companion object {
        const val TAG = "MyCustomService"
    }

    private lateinit var player: MediaPlayer

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI).apply {
            isLooping = true
        }
        Log.d(TAG, "Service create")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player.start()
        Log.d(TAG, "Service start")
        return START_STICKY
    }

    override fun onDestroy() {
        player.stop()
        Log.d(TAG, "Service destroy")
        super.onDestroy()
    }
}