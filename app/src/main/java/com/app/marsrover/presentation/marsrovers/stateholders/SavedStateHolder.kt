package com.app.marsrover.presentation.marsrovers.stateholders

import com.app.marsrover.domain.model.RoverPhotoUiModel

data class SavedStateHolder(
    val isLoading: Boolean = false,
    val data: List<RoverPhotoUiModel>? = null,
    val error: String = ""
) {
}