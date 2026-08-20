package com.orbit.aiNotifications

import com.orbit.aiNotifications.model.Content
import com.orbit.aiNotifications.model.GeminiRequest
import com.orbit.aiNotifications.model.Part
import com.orbit.other.Cons
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.orbit.BuildConfig

object GeminiClient {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC // set NONE in release builds
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Cons.AI_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(GeminiApi::class.java)

    suspend fun generateText(prompt: String): String {
        val response = api.generateContent(
            apiKey = BuildConfig.GEMINI_API_KEY,
            request = GeminiRequest(contents = listOf(Content(parts = listOf(Part(prompt)))))
        )

        return response.candidates
            ?.firstOrNull()
            ?.content?.parts?.firstOrNull()?.text
            ?.trim()
            ?: "Here's your reminder!"
    }
}