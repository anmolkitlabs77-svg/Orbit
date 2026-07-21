package com.example.jetpackcomposearchi.network.room_space.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.jetpackcomposearchi.network.room_space.entity.EventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Upsert
    fun insert(event: EventEntity)

    @Query("SELECT * FROM events ")
    fun getAllEvents(): Flow<List<EventEntity>>

}