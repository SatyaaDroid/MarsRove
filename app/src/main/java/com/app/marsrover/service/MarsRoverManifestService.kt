package com.app.marsrover.service

import com.app.marsrover.BuildConfig
import com.app.marsrover.service.model.RoverManifestRemoteModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface MarsRoverManifestService {

    @GET("mars-photos/api/v1/manifests/{rover_name}?api_key=DEMO_KEY")
    suspend fun getMarsRoverManifest(
        @Path("rover_name") roverName:String
    ):RoverManifestRemoteModel

    companion object{
        private const val BASE_API ="https://api.nasa.gov"

        fun create(): MarsRoverManifestService{
            val loader = HttpLoggingInterceptor()
            loader.level =
                if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
            val client = OkHttpClient.Builder()
                .addInterceptor(loader)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_API)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MarsRoverManifestService::class.java)
        }

    }
}