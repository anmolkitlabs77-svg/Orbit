package com.orbit.network

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.credentials.CreatePublicKeyCredentialRequest
import androidx.credentials.CreatePublicKeyCredentialResponse
import androidx.credentials.CredentialManager
import com.google.gson.Gson
import com.orbit.network.room_space.dao.ApodDao
import com.orbit.network.room_space.dao.EventDao
import com.orbit.network.room_space.dao.NeosDao
import com.orbit.network.room_space.dao.WeatherDeo
import com.orbit.network.room_space.entity.ApodEntity
import com.orbit.network.room_space.entity.EventEntity
import com.orbit.network.room_space.entity.NeosEntity
import com.orbit.network.room_space.entity.WeatherEntity
import com.orbit.other.Cons
import com.orbit.other.helper.formatKilometers
import com.orbit.other.helper.formatMeters
import com.orbit.other.helper.toFormattedVelocity
import com.orbit.register.model.RegisterRequest
import com.orbit.register.model.RegisterResponse
import com.orbit.register.model.RegisterVerifyRequest
import kotlinx.coroutines.launch
import javax.inject.Inject

class Repository @Inject constructor(val spaceDao: ApodDao,
                                     val neosDao: NeosDao,
                                     val eventDao: EventDao,
                                     val weatherDao: WeatherDeo) {
    val apiSpace : RetrofitApi = RetrofitClient.getSpaceRetrofit().create(RetrofitApi::class.java)
    val apiSpace2 : RetrofitApi = RetrofitClient.getSpaceRetrofit2().create(RetrofitApi::class.java)
    val apiAuth : RetrofitApi = RetrofitClient.getAuth().create(RetrofitApi::class.java)



    suspend fun syncSpaceData(start_date: String, end_date: String) {

        val response = apiSpace.getPicByDay(Cons.spaceToken, start_date, end_date)
        if(response.body() != null && response.body()?.size != 0) {
            response.body()?.forEach {
                spaceDao.insert(
                    ApodEntity(
                        date = it.date,
                        title = it.title,
                        explanation = it.explanation,
                        imageUrl = it.url,
                        copyright = it.copyright
                    )
                )
            }
        }
    }
    fun getSpaceData() = spaceDao.getAllApods()
    suspend fun getNeoByDays(today: String) {

        val response = apiSpace.getNeoByDay(Cons.spaceToken, today)

        if(response.isSuccessful && response.body() != null){

            response.body()?.near_earth_objects?.forEach { (date, list) ->
                val dateTime = date

                list.forEach { neo ->

                    val Status = when {
                        neo.is_potentially_hazardous_asteroid -> "Hazardous"
                        neo.is_sentry_object -> "Sentry"
                        else -> ""
                    }


                    neosDao.insert(
                        NeosEntity(
                            date = dateTime,
                            title = neo.name,
                            status = Status,
                            distance = neo.close_approach_data.get(0).miss_distance.kilometers.formatKilometers(),
                            velocity = neo.close_approach_data.get(0).relative_velocity.kilometers_per_hour.toFormattedVelocity(),
                            diameter = "${neo.estimated_diameter.kilometers.estimated_diameter_max.toString().formatMeters()} - ${neo.estimated_diameter.kilometers.estimated_diameter_min.toString().formatMeters()} ",
                            approachDate = neo.close_approach_data.get(0).close_approach_date_full
                        )
                    )
                    
                    println("${neo.name} on $date") 
                }
            }
        }
    }
    fun getNeos() = neosDao.getAllNeos()
    suspend fun Events(days: String){
        val response = apiSpace2.getEvents(days)

        if(response.isSuccessful && response.body() != null){
            response.body()?.events?.forEach {

                eventDao.insert(
                    EventEntity(
                        id = it.id
                    )
                )
            }
        }
    }
    fun getEvents() = eventDao.getAllEvents()

    suspend fun weather(){
        val response = apiSpace.getWeather(Cons.spaceToken)

        if(response.isSuccessful && response.body() != null){
            Log.d("Iddddddddddddd",response.body()?.get(0)?.messageId ?: "dfd")

            response.body()?.forEach {it->

                weatherDao.insert(
                    WeatherEntity(
                        messageId = it.messageId
                    )
                )
            }
        }
        else{
            Log.d("Iddddddddddddd","error")
        }
    }
    fun getWeather() = weatherDao.getAllWeather()



    suspend fun register(
        activity: Activity,
        request: RegisterRequest
    ): NetworkResult<RegisterResponse> {

        val response = try {
            NetworkResult.Success(apiAuth.register(request))
        } catch (e: Exception) {
            e.printStackTrace()
            return NetworkResult.Error("Network error: ${e.message}")
        }

        // response is NetworkResult.Success<RegisterResponse> here
        try {
            val credentialManager = CredentialManager.create(activity)
            val requestJsonString = Gson().toJson(response.data?.publicKey)

            val createRequest = CreatePublicKeyCredentialRequest(
                requestJson = requestJsonString
            )

            val credentialResponse = credentialManager.createCredential(
                context = activity,
                request = createRequest
            ) as CreatePublicKeyCredentialResponse

            apiAuth.registerVirfy(
                RegisterVerifyRequest(
                    email = request.email,
                    credential = credentialResponse.registrationResponseJson
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return NetworkResult.Error("Passkey creation failed: ${e.message}")
        }

        return response
    }
//    suspend fun register(
//        activity: Activity,
//        request: RegisterRequest
//    ): NetworkResult<RegisterResponse> {
//
//        val response = try {
//            apiAuth.register(request)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            return NetworkResult.Error(e.message ?: "Network error")
//        }
//
//        if (response !is NetworkResult.Success) {
//            return response
//        }
//
//        try {
//            val credentialManager = CredentialManager.create(activity)
//            val requestJsonString = Gson().toJson(response.data!!.publicKey)
//
//            val createRequest = CreatePublicKeyCredentialRequest(
//                requestJson = requestJsonString
//            )
//
//            val credentialResponse = credentialManager.createCredential(
//                context = activity,
//                request = createRequest
//            ) as CreatePublicKeyCredentialResponse
//
//            apiAuth.registerVirfy(
//                RegisterVerifyRequest(
//                    email = request.email,
//                    credential = credentialResponse.registrationResponseJson
//                )
//            )
//        } catch (e: Exception) {
//            e.printStackTrace()
//            return NetworkResult.Error(e.message ?: "Passkey creation failed")
//        }
//
//        return response
//    }
}
