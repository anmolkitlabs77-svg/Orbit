package com.orbit.network

import com.google.gson.GsonBuilder
import com.orbit.dashboard.base.App
import com.orbit.other.Cons
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Singleton
class RetrofitClient {

    companion object {

        fun getAuth(): Retrofit {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            return Retrofit.Builder()
                .baseUrl(Cons.BASE_URL_AUTH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getSpace())
                .build()
        }

        fun getSpaceRetrofit(): Retrofit {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            return Retrofit.Builder()
                .baseUrl(Cons.SPACE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getSpace())
                .build()
        }
        fun getSpaceRetrofit2(): Retrofit {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            return Retrofit.Builder()
                .baseUrl(Cons.SPACE_BASE_URL2)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getSpace())
                .build()
        }
        fun getSpace(): OkHttpClient {

            val logging = HttpLoggingInterceptor()
            /** set your desired log level */
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
            httpClient.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .cache(Cache(App.get().cacheDir, 10 * 1024 * 1024)) // 10 MB cache
                .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json").build()
                    val response = chain.proceed(request)
                    response
                })
            return httpClient.build()
        }
    }
}







