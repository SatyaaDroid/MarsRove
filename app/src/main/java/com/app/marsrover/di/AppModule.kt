package com.app.marsrover.di

import android.content.Context
import com.app.marsrover.data.remote.network.Api
import com.app.marsrover.data.repository.GetMarsManifestRepositoryImpl
import com.app.marsrover.data.repository.GetMarsRoverPhotoRepositoryImpl
import com.app.marsrover.data.db.MarsRoverSavedDatabase
import com.app.marsrover.data.db.dao.MarsRoverSavedPhotoDao
import com.app.marsrover.domain.use_cases.GetMarsRoverManifestUseCase
import com.app.marsrover.domain.repository.GetMarsRoverManifestRepository
import com.app.marsrover.domain.repository.GetMarsRoverPhotoRepository
import com.app.marsrover.domain.use_cases.GetMarsRoverPhotoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
        return Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): Api =
        retrofit.create(Api::class.java)

    @Provides
    fun providesGetMarsRoverRepository(api: Api): GetMarsRoverManifestRepository {
        return GetMarsManifestRepositoryImpl(api)
    }

    @Provides
    fun providesGetMarsManifestUseCase(getMarsRoverManifestRepository: GetMarsRoverManifestRepository): GetMarsRoverManifestUseCase {
        return GetMarsRoverManifestUseCase(getMarsRoverManifestRepository)
    }


    @Provides
    fun providesGetMarsRoverPhotoRepository(
        api: Api,
        marsRoverSavedPhotoDao: MarsRoverSavedPhotoDao
    ): GetMarsRoverPhotoRepository {
        return GetMarsRoverPhotoRepositoryImpl(api, marsRoverSavedPhotoDao)
    }

    @Provides
    fun providesGetMarsPhotosUseCase(getMarsRoverPhotoRepository: GetMarsRoverPhotoRepository): GetMarsRoverPhotoUseCase {
        return GetMarsRoverPhotoUseCase(getMarsRoverPhotoRepository)
    }


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