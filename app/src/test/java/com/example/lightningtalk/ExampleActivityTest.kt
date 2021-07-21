package com.example.lightningtalk

import android.os.Build
import android.widget.Button
import android.widget.Toast
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowService
import org.robolectric.shadows.ShadowToast

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.O])
class ExampleActivityTest {

    private lateinit var activity: ExampleActivity

    private lateinit var application: TestApplication

    @Before
    fun setup() {
        application = ApplicationProvider.getApplicationContext()
        activity = Robolectric.buildActivity(ExampleActivity::class.java).create().get()
    }

    @Test
    fun `Should show toast when button clicked`() {
        activity.findViewById<Button>(R.id.show_toast_button).performClick()

        Assert.assertTrue(ShadowToast.showedToast("Toast!!"))
        Assert.assertEquals(
            Toast.LENGTH_LONG,
            ShadowToast.getLatestToast().duration
        )
    }

    @Test
    fun `Should start intent service when button clicked`() {
        activity.findViewById<Button>(R.id.start_intent_service_button).performClick()

        Assert.assertEquals(
            "com.example.lightningtalk.MyCustomIntentService",
            ShadowService().nextStartedService.component?.className
        )
    }

    @Test
    fun `Should start service when button clicked`() {
        activity.findViewById<Button>(R.id.start_service_button).performClick()

        Assert.assertEquals(
            "com.example.lightningtalk.MyCustomService",
            ShadowService().nextStartedService.component?.className
        )
    }
}