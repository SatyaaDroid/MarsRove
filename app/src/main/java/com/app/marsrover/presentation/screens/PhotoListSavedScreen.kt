package com.app.marsrover.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.marsrover.presentation.viewmodel.savedlist.RoverSavedViewModel


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
                    roverSavedViewModel.changeSaveStatus(roverPhotoUiModel)
                }
            )
        }
    }

}