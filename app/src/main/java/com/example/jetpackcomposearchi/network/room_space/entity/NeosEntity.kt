package com.example.jetpackcomposearchi.network.room_space.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "neos")
data class NeosEntity(

    @PrimaryKey
    val date: String,
    val title: String,
    val status: String,
    val distance: String,
    val velocity: String,
    val diameter: String,
    val approachDate: String
    )
