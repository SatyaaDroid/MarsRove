package com.app.marsrover.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.marsrover.data.common.RoverPhotoUiState
import com.app.marsrover.presentation.viewmodel.photolist.MarsRoverPhotoViewModel
import com.app.marsrover.presentation.viewmodel.photolist.RoverPhotosViewModel


@Composable
fun PhotoScreen(
    modifier: Modifier,
    roverName: String?, sol: String?,
    roverPhotosViewModel: RoverPhotosViewModel
) {


    if (roverName != null && sol != null) {
        val result = roverPhotosViewModel.roverPhotosStateHolder.value

        if (result.isLoading) {
            Loading()
        }
        if (result.error.isNotBlank()) {
            Error()
        }

        result.data.let {
            if (it != null) {
                PhotoList(modifier = modifier, roverPhotoUiModelList = it) {

                }
            }

        }

    }
}

//@Composable
//fun PhotoScreen(
//    modifier: Modifier,
//    roverName: String?,
//    sol: String?,
//    marsRoverPhotoViewModel: MarsRoverPhotoViewModel
//) {
//
//    val viewState by marsRoverPhotoViewModel.roverPhotoUiState.collectAsStateWithLifecycle()
//
//
//    if (roverName != null && sol != null) {
//        LaunchedEffect(Unit) {
//            marsRoverPhotoViewModel.getMarsRoverPhoto(roverName, sol)
//        }
//        when (val roverPhotoUiState = viewState) {
//            RoverPhotoUiState.Error -> Error()
//            RoverPhotoUiState.Loading -> Loading()
//            is RoverPhotoUiState.Success -> PhotoList(
//                modifier = modifier,
//                roverPhotoUiModelList = roverPhotoUiState.roverPhotoUiModelList
//            ) { roverPhotoUiModel ->
//                marsRoverPhotoViewModel.changeSaveStatus(roverPhotoUiModel)
//            }
//        }
//    }
//}