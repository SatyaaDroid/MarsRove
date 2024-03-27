package com.app.marsrover.service.model

import com.google.gson.annotations.SerializedName


data class PhotoManifestRemoteModel(
    @field:SerializedName("landing_date") val landingDate: String,
    @field:SerializedName("lunch_Date") val lunchDate: String,
    @field:SerializedName("max_Date") val maxDate: String,
    @field:SerializedName("max_Sol") val maxSol: String,
    val name:String,
    val photos:List<ManifestPhotoRemoteModel>,
    val status:String,
    @field:SerializedName("total_photos") val totalPhotos:Int

    )
