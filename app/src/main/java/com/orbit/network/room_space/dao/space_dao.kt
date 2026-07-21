package com.orbit.network.room_space.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.orbit.network.room_space.entity.ApodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ApodDao {

    @Upsert
    suspend fun insert(apod: ApodEntity)

    @Query("SELECT * FROM apod ORDER BY date DESC")
    fun getAllApods(): Flow<List<ApodEntity>>

    @Query("SELECT * FROM apod WHERE date = :date")
    suspend fun getByDate(date: String): ApodEntity?

    @Query("SELECT * FROM apod ORDER BY date DESC LIMIT 1 ")
    suspend fun getLatestApod(): ApodEntity?

    @Query("DELETE FROM apod")
    suspend fun clear()

}