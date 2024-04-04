package com.app.marsrover.presentation.marsrovers.intent

sealed class RoverManifestIntent {
    data class LoadRoverManifest(val roverName: String) : RoverManifestIntent()
}