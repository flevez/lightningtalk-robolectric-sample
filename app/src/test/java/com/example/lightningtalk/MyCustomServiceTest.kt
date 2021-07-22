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
class MyCustomServiceTest {

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
        Robolectric.buildService(MyCustomService::class.java).create()

        verify {
            Log.d("MyCustomService", "Service create")
        }
    }

    @Test
    fun `Should log on onHandleIntent`() {
        Robolectric.buildService(MyCustomService::class.java).create().startCommand(0, 0)

        verify {
            Log.d("MyCustomService", "Service start")
        }
    }

    @Test
    fun `Should log on onDestroy`() {
        Robolectric.buildService(MyCustomService::class.java).create().destroy()

        verify {
            Log.d("MyCustomService", "Service destroy")
        }
    }

    @Test
    fun `Should stop player on onDestroy`() {
        Robolectric.buildService(MyCustomService::class.java).create().destroy()

        verify {
            player.stop()
        }
    }

    @Test
    fun `Should start player on onHandleIntent`() {
        Robolectric.buildService(MyCustomService::class.java).create().startCommand(0, 0)

        verify {
            player.start()
        }
    }

    @Test
    @Config(minSdk = Build.VERSION_CODES.O_MR1)
    fun `Should log that it is bigger than android oreo`() {
        Robolectric.buildService(MyCustomService::class.java).create().startCommand(0, 0)

        verify {
            Log.d("MyCustomService", "I'm bigger than Android Oreo")
        }
    }

    @Test
    @Config(maxSdk = Build.VERSION_CODES.N_MR1)
    fun `Should log that it is less than android oreo`() {
        Robolectric.buildService(MyCustomService::class.java).create().startCommand(0, 0)

        verify {
            Log.d("MyCustomService", "I'm less than Android Oreo")
        }
    }

    @Test
    @Config(sdk = [Build.VERSION_CODES.O])
    fun `Should log that it is android oreo`() {
        Robolectric.buildService(MyCustomService::class.java).create().startCommand(0, 0)

        verify {
            Log.d("MyCustomService", "I'm Android Oreo")
        }
    }
}