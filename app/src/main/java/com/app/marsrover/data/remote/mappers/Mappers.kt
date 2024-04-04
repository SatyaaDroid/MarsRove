package com.app.marsrover.data.remote.mappers


import com.app.marsrover.data.remote.model.ManifestPhotoRemoteModel
import com.app.marsrover.data.remote.model.RoverManifestRemoteModel
import com.app.marsrover.data.remote.model.RoverPhotoRemoteModel
import com.app.marsrover.domain.model.RoverManifestUiModel
import com.app.marsrover.domain.model.RoverPhotoUiModel



fun List<ManifestPhotoRemoteModel>.toDomain(): List<RoverManifestUiModel>{
    return map {
        RoverManifestUiModel(
            sol = it.sol.toString(),
            earthDate = it.earthDate,
            photoNumber = it.totalPhotos.toString()
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

