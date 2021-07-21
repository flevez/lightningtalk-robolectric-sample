package com.example.lightningtalk

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ExampleActivity : AppCompatActivity() {

    private val componentNameMyCustomService = ComponentName(
        "com.example.lightningtalk",
        "com.example.lightningtalk.MyCustomService"
    )
    private val componentNameMyCustomIntentService = ComponentName(
        "com.example.lightningtalk",
        "com.example.lightningtalk.MyCustomIntentService"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        findViewById<Button>(R.id.start_service_button).setOnClickListener {
            startService()
        }

        findViewById<Button>(R.id.start_intent_service_button).setOnClickListener {
            startIntentService()
        }

        findViewById<Button>(R.id.show_toast_button).setOnClickListener {
            showToast()
        }
    }

    override fun onStop() {
        super.onStop()
        stopService(Intent().apply {
            component = componentNameMyCustomService
        })
        stopService(Intent().apply {
            component = componentNameMyCustomIntentService
        })
    }

    private fun startService() {
        startService(Intent().apply {
            component = componentNameMyCustomService
        })
    }

    private fun startIntentService() {
        startService(Intent().apply {
            component = componentNameMyCustomIntentService
        })
    }

    private fun showToast() {
        Toast.makeText(this@ExampleActivity, "Toast!!", Toast.LENGTH_LONG).show()
    }
}