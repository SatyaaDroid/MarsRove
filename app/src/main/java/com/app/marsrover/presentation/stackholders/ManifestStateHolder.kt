package com.app.marsrover.presentation.stackholders

import com.app.marsrover.domain.model.RoverManifestUiModel

data class ManifestStateHolder(
    val isLoading: Boolean = false,
    val data: List<RoverManifestUiModel>? = null,
    val error: String = ""
)