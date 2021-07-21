package com.example.lightningtalk

import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.util.Log
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.O])
class MyCustomIntentServiceTest {

    @RelaxedMockK
    private lateinit var player: MediaPlayer

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mockkStatic(Log::class)
        mockkStatic(MediaPlayer::class)

        every { Log.d(any(), any()) } returns 0
        every { MediaPlayer.create(any(), any<Uri>()) } returns player
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `Should log on onCreate`() {
        Robolectric.buildIntentService(MyCustomIntentService::class.java).create()

        verify {
            Log.d("MyCustomIntentService", "IntentService create")
        }
    }

    @Test
    fun `Should log on onHandleIntent`() {
        Robolectric.buildIntentService(MyCustomIntentService::class.java).create().handleIntent()

        verify {
            Log.d("MyCustomIntentService", "IntentService start")
        }
    }

    @Test
    fun `Should log on onDestroy`() {
        Robolectric.buildIntentService(MyCustomIntentService::class.java).create().destroy()

        verify {
            Log.d("MyCustomIntentService", "IntentService destroy")
        }
    }

    @Test
    fun `Should stop player on onDestroy`() {
        Robolectric.buildIntentService(MyCustomIntentService::class.java).create().destroy()

        verify {
            player.stop()
        }
    }

    @Test
    fun `Should start player on onHandleIntent`() {
        Robolectric.buildIntentService(MyCustomIntentService::class.java).create().handleIntent()

        verify {
            player.start()
        }
    }
}