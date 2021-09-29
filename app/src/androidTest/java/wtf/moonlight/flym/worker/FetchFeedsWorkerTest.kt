package wtf.moonlight.flym.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.TestWorkerBuilder
import androidx.work.testing.WorkManagerTestInitHelper
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class FetchFeedsWorkerTest {
    private lateinit var context: Context
    private lateinit var executor: Executor

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        executor = Executors.newSingleThreadExecutor()
        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }

    @Test
    fun testFetchFeedsWorker() {
        val worker = TestWorkerBuilder<FetchFeedsWorker>(
            context = context,
            executor = executor
        ).setWorkerFactory(workerFactory).build()

        val result = worker.doWork()
        assertThat(result).isEqualTo(ListenableWorker.Result.success())
    }
}