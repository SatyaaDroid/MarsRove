package com.app.marsrover.domain.repository

import com.app.marsrover.domain.model.RoverManifestUiModel


interface GetMarsRoverManifestRepository {
    suspend fun getMarsRoverManifest(roverName: String): List<RoverManifestUiModel>


}