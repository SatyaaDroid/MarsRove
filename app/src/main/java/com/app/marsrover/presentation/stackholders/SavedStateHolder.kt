package com.app.marsrover.presentation.stackholders

import com.app.marsrover.domain.model.RoverPhotoUiModel

data class SavedStateHolder(
    val isLoading: Boolean = false,
    val data: List<RoverPhotoUiModel>? = null,
    val error: String = ""
) {
}