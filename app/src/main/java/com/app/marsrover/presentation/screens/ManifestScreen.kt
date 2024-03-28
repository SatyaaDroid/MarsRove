package com.app.marsrover.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.marsrover.data.common.RoverManifestUiState
import com.app.marsrover.domain.use_cases.GetMarsRoverManifestUseCase
import com.app.marsrover.presentation.viewmodel.manifestlist.MarsRoverManifestViewModel
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

//@Composable
//fun ManifestScreen(
//    modifier: Modifier,
//    roverName: String?,
//    marsRoverManifestViewModel: MarsRoverManifestViewModel,
//    onClick: (roverName: String, sol: String) -> Unit
//) {
//    val viewState by marsRoverManifestViewModel.roverManifestUiState.collectAsStateWithLifecycle()
//
//    if (roverName != null) {
//        LaunchedEffect(Unit) {
//            marsRoverManifestViewModel.getMarsRoverManifest(roverName)
//        }
//        when (val roverManifestUiState = viewState) {
//            RoverManifestUiState.Error -> Error()
//            RoverManifestUiState.Loading -> Loading()
//            is RoverManifestUiState.Success -> ManifestList(
//                modifier = modifier,
//                roverManifestUiModelList = roverManifestUiState.roverManifestUiModelList,
//                roverName = roverName,
//                onClick = onClick
//            )
//        }
//    }
//
//}