package com.app.marsrover.domain.repository

import com.app.marsrover.domain.model.RoverPhotoUiModel

interface GetMarsRoverPhotoRepository {
    suspend fun getMarsRoverPhotos(roverName: String, sol:String): List<RoverPhotoUiModel>
}