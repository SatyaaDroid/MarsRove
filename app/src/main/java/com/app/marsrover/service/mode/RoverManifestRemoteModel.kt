package com.app.marsrover.service.mode

import com.google.gson.annotations.SerializedName

data class RoverManifestRemoteModel(
    @field:SerializedName("photo_manifest") val photoManifestRemoteModel: PhotoManifestRemoteModel
)
