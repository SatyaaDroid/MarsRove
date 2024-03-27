package com.app.marsrover.service.model

import com.google.gson.annotations.SerializedName

data class CameraRemoteModel(
    @field:SerializedName("full_name") val fullName: String,
    val id: Int,
    val name: String,
    @field:SerializedName("rover_id") val roverID: Int
)
