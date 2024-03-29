package com.app.marsrover.data.common


data class RoverManifestUiModel(
    val sol:String,
    val earthDate:String,
    val photoNumber:String
):Comparable<RoverManifestUiModel>{
    override fun compareTo(other: RoverManifestUiModel): Int = other.earthDate.compareTo(this.earthDate)

}