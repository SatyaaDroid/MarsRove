package com.app.marsrover.data.converters

import com.app.marsrover.data.model.RoverManifestUiModel
import com.app.marsrover.data.model.RoverManifestUiState
import com.app.marsrover.service.model.RoverManifestRemoteModel

fun toUiModel(roverManifestRemoteModel: RoverManifestRemoteModel): RoverManifestUiState =
    RoverManifestUiState.Success(roverManifestRemoteModel.photoManifestRemoteModel.photos.map { photo ->
        RoverManifestUiModel(
            sol = photo.sol.toString(),
            earthDate = photo.earthDate.toString(),
            photoNumber = photo.totalPhotos.toString()
        )
    }.sorted())