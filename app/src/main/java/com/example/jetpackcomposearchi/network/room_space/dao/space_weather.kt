package com.example.jetpackcomposearchi.network.room_space.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.jetpackcomposearchi.network.room_space.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDeo {

    @Upsert
    fun insert(weather : WeatherEntity)

    @Query("SELECT * FROM weather ")
    fun getAllWeather(): Flow<List<WeatherEntity>>

}