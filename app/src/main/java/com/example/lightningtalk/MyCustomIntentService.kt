package com.example.lightningtalk

import android.app.IntentService
import android.content.Intent
import android.media.MediaPlayer
import android.provider.Settings
import android.util.Log

class MyCustomIntentService : IntentService("MyCustomIntentService") {
    companion object {
        const val TAG = "MyCustomIntentService"
    }

    private lateinit var player: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        player =
            MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI).apply {
                isLooping = true
            }
        Log.d(TAG, "IntentService create")
    }

    override fun onHandleIntent(p0: Intent?) {
        player.start()
        Log.d(TAG, "IntentService start")
    }

    override fun onDestroy() {
        player.stop()
        Log.d(TAG, "IntentService destroy")
        super.onDestroy()
    }
}