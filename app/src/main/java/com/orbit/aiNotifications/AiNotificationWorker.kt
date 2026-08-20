package com.orbit.aiNotifications

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

class AiNotificationWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val result = try {
            val prompt = "Write a short, friendly, one-sentence push notification (max 15 words) " +
                    "encouraging the user to open Orbit and check the latest updates."
            val text = GeminiClient.generateText(prompt)
            NotificationHelper.show(applicationContext, "Orbit", text)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }

        // Re-enqueue the next run regardless of success/failure so the loop continues
        scheduleNext(applicationContext)

        return result
    }

    companion object {
        private const val WORK_NAME = "ai_notification_work"
        private const val INTERVAL_MINUTES = 1L // bump this to 15+ later

        fun scheduleNext(context: Context) {
            val request = OneTimeWorkRequestBuilder<AiNotificationWorker>()
                .setInitialDelay(INTERVAL_MINUTES, TimeUnit.MINUTES)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()

            WorkManager.getInstance(context).enqueueUniqueWork(
                WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                request
            )
        }

        fun start(context: Context) {
            NotificationHelper.createChannel(context)
            val request = OneTimeWorkRequestBuilder<AiNotificationWorker>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()
            WorkManager.getInstance(context).enqueueUniqueWork(
                WORK_NAME,
                ExistingWorkPolicy.KEEP,
                request
            )
        }

        fun stop(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
        }
    }
}