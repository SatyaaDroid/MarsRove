package com.app.marsrover.presentation.marsrovers.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.marsrover.core.common.Resource
import com.app.marsrover.di.IoDispatcher
import com.app.marsrover.domain.model.RoverPhotoUiModel
import com.app.marsrover.domain.use_cases.GetMarsRoverPhotoUseCase
import com.app.marsrover.presentation.marsrovers.intent.RoverPhotosIntent
import com.app.marsrover.presentation.marsrovers.stateholders.PhotosStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RoverPhotosViewModel @Inject constructor(
    private val getMarsRoverPhotoUseCase: GetMarsRoverPhotoUseCase,
    savedStateHandle: SavedStateHandle,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _roverPhotosStateHolder = mutableStateOf(PhotosStateHolder())
    val roverPhotosStateHolder: State<PhotosStateHolder> = _roverPhotosStateHolder

    init {
        viewModelScope.launch {
            savedStateHandle.get<String>("roverName")?.let { roverName ->
                savedStateHandle.get<String>("sol")?.let { sol ->
                    if (!roverName.isNullOrBlank() && !sol.isNullOrBlank()) {
                        processIntent(RoverPhotosIntent.LoadRoverPhotos(roverName, sol))
                    }
//                    getRoverPhotosList(roverName, sol)
                }
            }
        }
    }

    fun processIntent(intent: RoverPhotosIntent) {
        when (intent) {
            is RoverPhotosIntent.LoadRoverPhotos -> getRoverPhotosList(intent.roverName, intent.sol)
            is RoverPhotosIntent.ChangeSaveStatus -> changeSaveStatus(intent.roverPhotoUiModel)
        }
    }

    private fun getRoverPhotosList(roverName: String, sol: String) = viewModelScope.launch {
        getMarsRoverPhotoUseCase(roverName, sol).onEach {
            when (it) {
                is Resource.Error -> {
                    _roverPhotosStateHolder.value = PhotosStateHolder(
                        error = it.message.toString()
                    )
                }

                is Resource.Loading -> {
                    _roverPhotosStateHolder.value = PhotosStateHolder(
                        isLoading = true
                    )

                }

                is Resource.Success -> {
                    _roverPhotosStateHolder.value = PhotosStateHolder(
                        data = it.data
                    )

                }
            }
        }.launchIn(viewModelScope)

    }

    fun changeSaveStatus(roverPhotoUiModel: RoverPhotoUiModel) {
        viewModelScope.launch {
            if (roverPhotoUiModel.isSaved) {
                getMarsRoverPhotoUseCase.removePhoto(roverPhotoUiModel)
            } else {
                getMarsRoverPhotoUseCase.savePhoto(roverPhotoUiModel)
            }
        }
    }
}