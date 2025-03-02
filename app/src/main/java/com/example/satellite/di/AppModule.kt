package com.example.satellite.di

import android.content.Context
import androidx.room.Room
import com.example.satellite.data.local.SatelliteDatabase
import com.example.satellite.data.local.SatelliteDetailDao
import com.example.satellite.data.repository.SatelliteRepository
import com.example.satellite.domain.GetSatelliteDetailUseCase
import com.example.satellite.domain.GetSatelliteListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideSatelliteRepository(
        satelliteDao: SatelliteDetailDao,
        @ApplicationContext context: Context
    ): SatelliteRepository {
        return SatelliteRepository(satelliteDao, context)
    }


    @Provides
    fun provideGetSatelliteListUseCase(repository: SatelliteRepository): GetSatelliteListUseCase {
        return GetSatelliteListUseCase(repository)
    }

    @Provides
    fun provideGetSatelliteDetailUseCase(repository: SatelliteRepository): GetSatelliteDetailUseCase {
        return GetSatelliteDetailUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SatelliteDatabase {
        return Room.databaseBuilder(
            context,
            SatelliteDatabase::class.java,
            "satellite_db"
        ).build()
    }

    @Provides
    fun provideSatelliteDao(database: SatelliteDatabase): SatelliteDetailDao {
        return database.satelliteDetailDao()
    }
}