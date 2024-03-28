package com.app.marsrover.data.repository

import com.app.marsrover.data.common.RoverManifestUiState
import com.app.marsrover.data.converters.toUiModel
import com.app.marsrover.data.network.Api
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MarsRoverManifestRepo @Inject constructor(
    private val api: Api
) {
    fun getMarsRoverManifest(roverName: String): Flow<RoverManifestUiState> = flow {
        try {
            emit(
                toUiModel(
                    api.getMarsRoverManifest(
                        roverName
                    )
                )
            )
        } catch (throwable: Throwable) {
            emit(RoverManifestUiState.Error)
        }

    }
}