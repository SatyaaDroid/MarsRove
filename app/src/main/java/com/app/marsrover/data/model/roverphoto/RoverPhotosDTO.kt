package com.app.marsrover.data.model.roverphoto

data class RoverPhotosDTO(
    val id: Int,
    val roverName: String,
    val imgSrc: String,
    val sol: String,
    val earthDate: String,
    val cameraFullName: String,
    val isSaved: Boolean
)
