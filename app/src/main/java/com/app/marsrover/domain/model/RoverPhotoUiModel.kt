package com.app.marsrover.domain.model

data class RoverPhotoUiModel(
    val id: Int,
    val roverName: String,
    val imgSrc: String,
    val sol: String,
    val earthDate: String,
    val cameraFullName: String,
    val isSaved :Boolean
)