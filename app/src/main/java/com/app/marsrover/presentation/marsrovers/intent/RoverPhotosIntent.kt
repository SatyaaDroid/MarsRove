package com.app.marsrover.presentation.marsrovers.intent

import com.app.marsrover.domain.model.RoverPhotoUiModel

sealed class RoverPhotosIntent {
    data class LoadRoverPhotos(val roverName: String, val sol: String) : RoverPhotosIntent()
    data class ChangeSaveStatus(val roverPhotoUiModel: RoverPhotoUiModel) : RoverPhotosIntent()
}
