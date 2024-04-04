package com.app.marsrover.presentation.marsrovers.intent

import com.app.marsrover.domain.model.RoverPhotoUiModel

sealed class RoverSavedIntent {
    object LoadSavedPhotos : RoverSavedIntent()
    data class ChangeSaveStatus(val roverPhotoUiModel: RoverPhotoUiModel) : RoverSavedIntent()
}
