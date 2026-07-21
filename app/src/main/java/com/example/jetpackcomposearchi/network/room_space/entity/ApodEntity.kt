package com.example.jetpackcomposearchi.network.room_space.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apod")
data class ApodEntity(

    @PrimaryKey
    val date: String,

    val title: String,

    val explanation: String,

    val imageUrl: String,

    val copyright: String?,

)