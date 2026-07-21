package com.example.jetpackcomposearchi.network.room_space

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.jetpackcomposearchi.R
import com.example.jetpackcomposearchi.network.Repository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope

@HiltWorker
class SpaceWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: Repository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = supervisorScope {

        setForeground(createNotification(applicationContext))

        val startDate = inputData.getString("START_DATE") ?: ""
        val endDate = inputData.getString("END_DATE") ?: ""


            awaitAll(
                async {
                    runCatching { repository.syncSpaceData(startDate, endDate) }
                        .onFailure { Log.e("Worker", "sync failed", it) }
                },
                async {
                    runCatching { repository.getNeoByDays(endDate) }
                        .onFailure { Log.e("Worker", "neo failed", it) }
                },
                async {
                    runCatching { repository.Events("7") }
                        .onFailure { Log.e("Worker", "events failed", it) }
                },
                async {
                    runCatching { repository.weather() }
                        .onFailure { Log.e("Worker", "weather failed", it) }
                }
            )
            Result.success()
    }
}


private fun createNotification(context: Context): ForegroundInfo {

    // v2 forces a fresh channel — the old IMPORTANCE_LOW one is baked into the device
    val channelId = "space_sync_channel_v2"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            "Space Sync",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        context.getSystemService(NotificationManager::class.java)
            .createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_wifi_off)
        .setContentTitle("Syncing Space Data")
        .setContentText("Please wait...")
        .setAutoCancel(false)
        .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
        .build()

    return ForegroundInfo(1001, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC)
}
