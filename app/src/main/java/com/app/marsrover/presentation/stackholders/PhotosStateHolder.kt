package com.app.marsrover.presentation.stackholders

import com.app.marsrover.domain.model.RoverPhotoUiModel

class PhotosStateHolder(
    val isLoading: Boolean = false,
    val data: List<RoverPhotoUiModel>? = null,
    val error: String = ""
) {
}