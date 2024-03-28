package com.app.marsrover.data.mappers


import com.app.marsrover.data.model.RoverManifestRemoteModel
import com.app.marsrover.data.model.RoverPhotoRemoteModel
import com.app.marsrover.domain.model.RoverManifestUiModel
import com.app.marsrover.domain.model.RoverPhotoUiModel


fun RoverManifestRemoteModel.toDomain(): List<RoverManifestUiModel> {
    return this.photoManifestRemoteModel.photos.map { photo ->
        RoverManifestUiModel(
            sol = photo.sol.toString(),
            earthDate = photo.earthDate.toString(),
            photoNumber = photo.totalPhotos.toString()
        )
    }
}


fun RoverPhotoRemoteModel.toDomain(): List<RoverPhotoUiModel> {
    return this.photos.map { it ->
        RoverPhotoUiModel(
            id = it.id,
            roverName = it.rover.name,
            imgSrc = it.imgSrc,
            sol = it.sol.toString(),
            earthDate = it.earthDate,
            cameraFullName = it.camera.fullName,
            isSaved = false
        )

    }
}

