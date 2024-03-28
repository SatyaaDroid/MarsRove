package com.app.marsrover.presentation.viewmodel.manifestlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.marsrover.data.repository.MarsRoverManifestRepo
import com.app.marsrover.di.IoDispatcher
import com.app.marsrover.data.common.RoverManifestUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarsRoverManifestViewModel @Inject constructor(
    private val marsRoverManifestRepo: MarsRoverManifestRepo,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _roverManifestUiState: MutableStateFlow<RoverManifestUiState> =
        MutableStateFlow(RoverManifestUiState.Loading)

    val roverManifestUiState: StateFlow<RoverManifestUiState> = _roverManifestUiState

    fun getMarsRoverManifest(roverName: String) {
        viewModelScope.launch(ioDispatcher) {
            _roverManifestUiState.value = RoverManifestUiState.Loading
            marsRoverManifestRepo.getMarsRoverManifest(roverName).collect {
                _roverManifestUiState.value = it

            }
        }
    }
}