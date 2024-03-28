package com.app.marsrover.data.repository

import com.app.marsrover.data.mappers.toDomain
import com.app.marsrover.data.network.Api
import com.app.marsrover.domain.model.RoverPhotoUiModel
import com.app.marsrover.domain.repository.GetMarsRoverPhotoRepository
import javax.inject.Inject

class GetMarsRoverPhotoRepositoryImpl @Inject constructor(
    private val api: Api
) : GetMarsRoverPhotoRepository {


    override suspend fun getMarsRoverPhotos(
        roverName: String,
        sol: String
    ): List<RoverPhotoUiModel> {
        val roverPhotoRemoteModel = api.getMarsRoverPhotos(roverName, sol)
        return roverPhotoRemoteModel.toDomain()
    }


}