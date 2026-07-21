package com.example.jetpackcomposearchi.network.room_space.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.jetpackcomposearchi.network.room_space.entity.NeosEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NeosDao {

    @Upsert
    fun insert(noes : NeosEntity)

    @Query("SELECT * FROM neos ORDER BY date DESC")
    fun getAllNeos(): Flow<List<NeosEntity>>

}