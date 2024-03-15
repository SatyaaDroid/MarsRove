package com.app.marsrover.di

import android.content.Context
import com.app.marsrover.db.MarsRoverSavedDatabase
import com.app.marsrover.db.MarsRoverSavedPhotoDao
import com.app.marsrover.service.MarsRoverManifestService
import com.app.marsrover.service.MarsRoverPhotoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideMarsRoverManifestService(): MarsRoverManifestService =
        MarsRoverManifestService.create()

    @Provides
    fun provideMarsRoverPhotosService(): MarsRoverPhotoService =
        MarsRoverPhotoService.create()

    @Provides
    fun provideMarsRoverSavedPhotoDao(@ApplicationContext context: Context): MarsRoverSavedPhotoDao =
        MarsRoverSavedDatabase.getInstance(context).marsRoverSavedPhotoDao()

    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher