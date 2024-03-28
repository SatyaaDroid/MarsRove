package com.app.marsrover.domain.repository

import com.app.marsrover.domain.model.RoverPhotoUiModel
import kotlinx.coroutines.flow.Flow

interface GetMarsRoverPhotoRepository {
    suspend fun getMarsRoverPhotos(roverName: String, sol:String): List<RoverPhotoUiModel>

    suspend fun savePhoto(roverPhotoUiModel: RoverPhotoUiModel)

    suspend fun removePhoto(roverPhotoUiModel: RoverPhotoUiModel)

    suspend fun allSavedIds(sol:String,roverName: String): Flow<List<Int>>

    suspend fun getAllSavedData():Flow<List<RoverPhotoUiModel>>


}