package com.app.marsrover.model

import com.app.marsrover.service.mode.RoverManifestRemoteModel

fun toUiModel(roverManifestRemoteModel: RoverManifestRemoteModel): RoverManifestUiState =
    RoverManifestUiState.Success(roverManifestRemoteModel.photoManifestRemoteModel.photos.map { photo ->
        RoverManifestUiModel(
            sol = photo.sol.toString(),
            earthDate = photo.earthDate.toString(),
            photoNumber = photo.totalPhotos.toString()
        )
    }.sorted())