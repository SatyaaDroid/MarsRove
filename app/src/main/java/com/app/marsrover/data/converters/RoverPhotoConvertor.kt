package com.app.marsrover.data.converters

import com.app.marsrover.db.MarsRoverSavedLocalModel
import com.app.marsrover.domain.model.RoverPhotoUiModel

fun toDbModel(roverPhotoUiModel: RoverPhotoUiModel): MarsRoverSavedLocalModel =
    MarsRoverSavedLocalModel(
        roverPhotoId = roverPhotoUiModel.id,
        roverName = roverPhotoUiModel.roverName,
        imgSrc = roverPhotoUiModel.imgSrc,
        sol = roverPhotoUiModel.sol,
        earthDate = roverPhotoUiModel.earthDate,
        cameraFullName = roverPhotoUiModel.cameraFullName,
    )

fun toUiModel(marsRoverSavedLocalModelList: List<MarsRoverSavedLocalModel>) =
    marsRoverSavedLocalModelList.map { photo ->
        RoverPhotoUiModel(
            id = photo.roverPhotoId,
            roverName = photo.roverName,
            imgSrc = photo.imgSrc,
            sol = photo.sol,
            earthDate = photo.earthDate,
            cameraFullName = photo.cameraFullName,
            isSaved = true
        )

    }