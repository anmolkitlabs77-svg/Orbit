package com.orbit.network.room_space

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.orbit.R
import com.orbit.network.Repository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope

private const val CHANNEL_ID = "space_sync_channel_v2"
private const val FOREGROUND_NOTIFICATION_ID = 1001
private const val FAILURE_NOTIFICATION_ID = 1002

@HiltWorker
class SpaceWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: Repository
) : CoroutineWorker(appContext, workerParams) {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun doWork(): Result = supervisorScope {

        setForeground(createForegroundInfo(applicationContext))

        val failures = mutableListOf<String>()

        val startDate = inputData.getString("START_DATE") ?: ""
        val endDate = inputData.getString("END_DATE") ?: ""

        awaitAll(
            async {
                runCatching { repository.syncSpaceData(startDate, endDate) }
                    .onFailure {
                        Log.e("Worker", "sync failed", it)
                        failures.add("Space data")
                    }
            },
            async {
                runCatching { repository.getNeoByDays(endDate) }
                    .onFailure {
                        Log.e("Worker", "neo failed", it)
                        failures.add("NEO data")
                    }
            },
            async {
                runCatching { repository.Events("7") }
                    .onFailure {
                        Log.e("Worker", "events failed", it)
                        failures.add("Events")
                    }
            },
            async {
                runCatching { repository.weather() }
                    .onFailure {
                        Log.e("Worker", "weather failed", it)
                        failures.add("Weather")
                    }
            }
        )

        if (failures.isNotEmpty()) {
            showSyncFailureNotification(applicationContext, failures)
        }

        Result.success()
    }
}

private fun ensureSyncChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Space Sync",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        context.getSystemService(NotificationManager::class.java)
            .createNotificationChannel(channel)
    }
}

private fun createForegroundInfo(context: Context): ForegroundInfo {
    ensureSyncChannel(context)

    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.logo)
        .setContentTitle("Syncing Space Data")
        .setContentText("Please wait...")
        .setAutoCancel(false)
        .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
        .build()

    return ForegroundInfo(
        FOREGROUND_NOTIFICATION_ID,
        notification,
        ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
    )
}

private fun showSyncFailureNotification(context: Context, failures: List<String>) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        Log.w("Worker", "POST_NOTIFICATIONS not granted, skipping failure notification")
        return
    }

    ensureSyncChannel(context)

    val text = if (failures.size == 4) {
        "Sync failed — check your connection"
    } else {
        "Couldn't update: ${failures.joinToString(", ")} (Try to update api key)"
    }

    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.logo)
        .setContentTitle("Sync incomplete")
        .setContentText(text)
        .setStyle(NotificationCompat.BigTextStyle().bigText(text))
        .setAutoCancel(true)
        .build()

    NotificationManagerCompat.from(context).notify(FAILURE_NOTIFICATION_ID, notification)
}