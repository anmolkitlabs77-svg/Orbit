package com.orbit.network

import com.orbit.dashboard.apod.model.picdayModel
import com.orbit.dashboard.events.model.Events
import com.orbit.dashboard.neos.model.neosModel
import com.orbit.dashboard.weather.model.weather
import com.orbit.login.model.LoginResponse
import com.orbit.login.model.LoginStartRequest
import com.orbit.login.model.LoginVerifyRequest
import com.orbit.login.model.LoginVerifyResponse
import com.orbit.register.model.RegisterRequest
import com.orbit.register.model.RegisterResponse
import com.orbit.register.model.RegisterVerifyRequest
import okhttp3.ResponseBody

import retrofit2.Response
import retrofit2.http.*

interface RetrofitApi {

    @GET("planetary/apod")
    suspend fun getPicByDay(
        @Query("api_key") api_key: String,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String
    ): Response<List<picdayModel>>

    @GET("neo/rest/v1/feed")
    suspend fun getNeoByDay(
        @Query("api_key") api_key: String,
        @Query("start_date") start_date: String
    ): Response<neosModel>

    @GET("events")
    suspend fun getEvents(
        @Query("days") days: String
    ): Response<Events>

    @GET("DONKI/notifications")
    suspend fun getWeather(
        @Query("api_key") api_key: String
    ) : Response<List<weather>>

    @POST("passkey/register/options")
    suspend fun register(
        @Body request: RegisterRequest
    ) :  RegisterResponse

    @POST("passkey/register/verify")
    suspend fun registerVirfy(
        @Body request : RegisterVerifyRequest
    ) : Response<LoginVerifyResponse>

    @POST("passkey/login/start")
    suspend fun loginStart(
        @Body request: LoginStartRequest
    ): Response<LoginResponse>

    @POST("passkey/login/verify")
    suspend fun loginVerify(
        @Body request: LoginVerifyRequest
    ): Response<LoginVerifyResponse>

}



