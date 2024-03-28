package com.app.marsrover.data.local

import com.app.marsrover.R

data class RoverUiModel(
    val name: String,
    val img: Int,
    val landingDate: String,
    val distance: String
)

val roverUiModelList = listOf(
    RoverUiModel(
        "Perseverance",
        R.drawable.perseverance,
        landingDate = "18 February 2021",
        distance = "12.56 km"
    ),
    RoverUiModel(
        "Curiosity",
        R.drawable.curiosity,
        landingDate = "6 August 2012",
        distance = "29.56 km"
    ),
    RoverUiModel(
        "Opportunity",
        R.drawable.opportunity,
        landingDate = "25 January 2004",
        distance = "45 km"
    ),
    RoverUiModel("Spirit", R.drawable.spirit, landingDate = "4 January", distance = "7.56 km"),
)