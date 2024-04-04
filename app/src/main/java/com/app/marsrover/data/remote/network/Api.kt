package com.app.marsrover.data.remote.network

import com.app.marsrover.data.remote.model.RoverManifestRemoteModel
import com.app.marsrover.data.remote.model.RoverPhotoRemoteModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    companion object {
        const val BASE_URL = "https://api.nasa.gov"
    }


    @GET("mars-photos/api/v1/manifests/{rover_name}?api_key=DEMO_KEY")
    suspend fun getMarsRoverManifest(
        @Path("rover_name") roverName:String
    ): RoverManifestRemoteModel


    @GET("mars-photos/api/v1/rovers/{rover_name}/photos?api_key=DEMO_KEY")
    suspend fun getMarsRoverPhotos(
        @Path("rover_name") roverName: String,
        @Query("sol") sol: String
    ): RoverPhotoRemoteModel

}