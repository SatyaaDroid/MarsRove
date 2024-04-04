package com.app.marsrover.presentation.marsrovers.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.marsrover.presentation.marsrovers.intent.RoverPhotosIntent
import com.app.marsrover.presentation.marsrovers.viewmodel.RoverPhotosViewModel


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
                PhotoList(modifier = modifier,
                    roverPhotoUiModelList = it
                ) {roverPhotoUiModel ->
                    val intent = RoverPhotosIntent.ChangeSaveStatus(roverPhotoUiModel)
                    roverPhotosViewModel.processIntent(intent)
                }
            }

        }

    }
}
