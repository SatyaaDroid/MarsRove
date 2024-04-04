package com.app.marsrover.presentation.marsrovers.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.marsrover.core.common.Resource
import com.app.marsrover.di.IoDispatcher
import com.app.marsrover.domain.use_cases.GetMarsRoverManifestUseCase
import com.app.marsrover.presentation.marsrovers.intent.RoverManifestIntent
import com.app.marsrover.presentation.marsrovers.stateholders.ManifestStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
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
        viewModelScope.launch(ioDispatcher) {
            savedStateHandle.getStateFlow("roverName", "").collectLatest {
//                getRoverManifestList(it)
                processIntent(RoverManifestIntent.LoadRoverManifest(it))
            }
        }

    }

    private fun processIntent(intent: RoverManifestIntent) {
        when (intent) {
            is RoverManifestIntent.LoadRoverManifest -> getRoverManifestList(intent.roverName)
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