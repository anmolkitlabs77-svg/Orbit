package com.example.jetpackcomposearchi.network.room_space.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(

    @PrimaryKey
    val messageId: String,

)
