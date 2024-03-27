package com.app.marsrover.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.marsrover.R
import com.app.marsrover.data.model.roverUiModelList

@Composable
fun RoverList(
    modifier: Modifier,
    onClick: (roverName: String) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn {
            items(count = roverUiModelList.size, itemContent = { index ->
                Rover(
                    name = roverUiModelList[index].name,
                    img = roverUiModelList[index].img,
                    ladingDate = roverUiModelList[index].landingDate,
                    distancetraveled = roverUiModelList[index].distance,
                    onClick = onClick
                )
            })
        }
    }
}

@Preview
@Composable
fun RoverPreview() {
    Rover("Preseverance", R.drawable.perseverance, "18 Feb 1202", "35.90 Km",
        {})
}

@Composable
fun Rover(
    name: String,
    img: Int,
    ladingDate: String,
    distancetraveled: String,
    onClick: (roverName: String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable { onClick(name) },
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )
            Image(painter = painterResource(id = img), contentDescription = null)
            Text(text = "credit: NASA/JPL", style = MaterialTheme.typography.labelSmall)
            Text(text = "Lading date: $ladingDate", style = MaterialTheme.typography.bodySmall)
            Text(
                text = "Distance traveled: $distancetraveled",
                style = MaterialTheme.typography.bodySmall
            )

        }


    }
}