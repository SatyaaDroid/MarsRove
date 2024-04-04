package com.app.marsrover.presentation.marsrovers.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.marsrover.presentation.marsrovers.intent.RoverSavedIntent
import com.app.marsrover.presentation.marsrovers.viewmodel.RoverSavedViewModel


@Composable
fun PhotoListSavedScreen(
    modifier: Modifier = Modifier,
    roverSavedViewModel: RoverSavedViewModel
) {



    val result = roverSavedViewModel.savedPhotosStateHolder.value

    if (result.isLoading) {
        Loading()
    }

    if (result.error.isNotBlank()) {
        Error()
    }

    result.data.let {
        if (it != null) {
            PhotoList(modifier = modifier,
                roverPhotoUiModelList = it,
                onClick = {roverPhotoUiModel->
                    val intent = RoverSavedIntent.ChangeSaveStatus(roverPhotoUiModel)
                    roverSavedViewModel.processIntent(intent)
                }
            )
        }
    }

}