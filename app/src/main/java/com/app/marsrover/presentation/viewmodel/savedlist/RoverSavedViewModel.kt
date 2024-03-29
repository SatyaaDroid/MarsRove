package com.app.marsrover.presentation.viewmodel.savedlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.marsrover.domain.model.RoverPhotoUiModel
import com.app.marsrover.domain.use_cases.GetMarsRoverPhotoUseCase
import com.app.marsrover.presentation.stackholders.SavedStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RoverSavedViewModel @Inject constructor(
    private val getMarsRoverPhotoUseCase: GetMarsRoverPhotoUseCase
) : ViewModel() {

    private val _savedPhotosStateHolder = mutableStateOf(SavedStateHolder())
    val savedPhotosStateHolder: State<SavedStateHolder> = _savedPhotosStateHolder

    init {
        getAllSavedDataFromDB()
    }

    private fun getAllSavedDataFromDB() {
        viewModelScope.launch {
            _savedPhotosStateHolder.value = SavedStateHolder(isLoading = true)
            try {
                getMarsRoverPhotoUseCase.getAllSavedData().collect {
                    _savedPhotosStateHolder.value = SavedStateHolder(
                        data = it
                    )
                }
            } catch (e: Exception) {
                _savedPhotosStateHolder.value = SavedStateHolder(error = e.message.toString())
            }
        }

    }

    fun changeSaveStatus(roverPhotoUiModel: RoverPhotoUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            if (roverPhotoUiModel.isSaved) {
                getMarsRoverPhotoUseCase.removePhoto(roverPhotoUiModel)
            } else {
                getMarsRoverPhotoUseCase.savePhoto(roverPhotoUiModel)
            }

        }

    }

}