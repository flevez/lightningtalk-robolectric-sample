package com.example.lightningtalk

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ExampleActivity : AppCompatActivity() {

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

    private fun startService() {
        startService(getIntentMyCustomService())
    }

    private fun startIntentService() {
        startService(getIntentMyCustomIntentService())
    }

    private fun showToast() {
        Toast.makeText(this@ExampleActivity, "Toast!!", Toast.LENGTH_LONG).show()
    }

    override fun onStop() {
        stopService(getIntentMyCustomService())
        stopService(getIntentMyCustomIntentService())
        super.onStop()
    }

    private fun getIntentMyCustomService() = Intent().apply {
        component = ComponentName(
            "com.example.lightningtalk",
            "com.example.lightningtalk.MyCustomService"
        )
    }

    private fun getIntentMyCustomIntentService() = Intent().apply {
        component = ComponentName(
            "com.example.lightningtalk",
            "com.example.lightningtalk.MyCustomIntentService"
        )
    }
}