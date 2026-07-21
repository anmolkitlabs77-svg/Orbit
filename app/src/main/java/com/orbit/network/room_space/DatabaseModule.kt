package com.orbit.network.room_space

import android.content.Context
import androidx.room.Room
import com.orbit.network.room_space.dao.ApodDao
import com.orbit.network.room_space.dao.EventDao
import com.orbit.network.room_space.dao.NeosDao
import com.orbit.network.room_space.dao.WeatherDeo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): SpaceDatabase {

        return Room.databaseBuilder(
            context,
            SpaceDatabase::class.java,
            "apod_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideApodDao(
        db: SpaceDatabase
    ): ApodDao {
        return db.apodDao()
    }

    @Provides
    fun providerNeosDao(db: SpaceDatabase): NeosDao {
        return db.neosDao()
    }

    @Provides
    fun providerEventsDao(db: SpaceDatabase): EventDao {
        return db.eventsDao()
    }

    @Provides
    fun providerWeatherDao(db: SpaceDatabase): WeatherDeo {
        return db.weatherDao()
    }
}