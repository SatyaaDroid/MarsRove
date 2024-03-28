package com.app.marsrover.presentation.viewmodel.manifestlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.marsrover.data.common.Resource
import com.app.marsrover.data.common.RoverManifestUiState
import com.app.marsrover.data.common.RoverPhotoUiModel
import com.app.marsrover.data.common.RoverPhotoUiState
import com.app.marsrover.data.converters.toUiModel
import com.app.marsrover.di.IoDispatcher
import com.app.marsrover.presentation.stackholders.ManifestStateHolder
import com.app.marsrover.domain.use_cases.GetMarsRoverManifestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RoverManifestViewModel @Inject constructor(
    private val getMarsRoverManifestUseCase: GetMarsRoverManifestUseCase,
    savedStateHandle: SavedStateHandle,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {


    private val _roverManifeststateHolder = mutableStateOf(ManifestStateHolder())
    val roverManifestStateHolder: State<ManifestStateHolder> = _roverManifeststateHolder

    init {
        viewModelScope.launch {
            savedStateHandle.getStateFlow("roverName", "").collectLatest {
                getRoverManifestList(it)
            }
        }

    }

    private fun getRoverManifestList(roverName: String) = viewModelScope.launch {
        getMarsRoverManifestUseCase(roverName).onEach {
            when (it) {
                is Resource.Error -> {
                    _roverManifeststateHolder.value =
                        ManifestStateHolder(error = it.message.toString())
                }

                is Resource.Loading -> {
                    _roverManifeststateHolder.value = ManifestStateHolder(isLoading = true)
                }

                is Resource.Success -> {
                    _roverManifeststateHolder.value = ManifestStateHolder(data = it.data)
                }
            }
        }.launchIn(viewModelScope)

    }


}