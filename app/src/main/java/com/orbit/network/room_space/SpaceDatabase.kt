package com.orbit.network.room_space

import androidx.room.Database
import androidx.room.RoomDatabase
import com.orbit.network.room_space.dao.ApodDao
import com.orbit.network.room_space.dao.EventDao
import com.orbit.network.room_space.dao.NeosDao
import com.orbit.network.room_space.dao.WeatherDeo
import com.orbit.network.room_space.entity.ApodEntity
import com.orbit.network.room_space.entity.EventEntity
import com.orbit.network.room_space.entity.NeosEntity
import com.orbit.network.room_space.entity.WeatherEntity

@Database(
    entities = [ApodEntity::class, NeosEntity::class, EventEntity::class, WeatherEntity::class],
    version = 7,
    exportSchema = false
)
abstract class SpaceDatabase : RoomDatabase() {

    abstract fun apodDao(): ApodDao
    abstract fun neosDao(): NeosDao
    abstract fun eventsDao(): EventDao
    abstract fun weatherDao(): WeatherDeo

}