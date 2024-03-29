package com.app.marsrover.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.marsrover.presentation.viewmodel.manifestlist.RoverManifestViewModel


@Composable
fun ManifestScreen(
    modifier: Modifier,
    roverName: String?,
    roverManifestViewModel: RoverManifestViewModel,
    onClick: (roverName: String, sol: String) -> Unit
) {

//    val viewState by getMarsRoverManifestUseCase.roverManifestUiState.collectAsStateWithLifecycle()
    if (roverName != null) {

        val result = roverManifestViewModel.roverManifestStateHolder.value

        if (result.isLoading) {
            Loading()
        }
        if (result.error.isNotBlank()) {
            Error()
        }
        result.data?.let {
            ManifestList(
                modifier = modifier,
                roverManifestUiModelList = it,
                roverName = roverName,
                onClick = onClick
            )

        }

    }

}

