package com.app.marsrover.roverPhotos

import com.app.marsrover.MainCoroutineRule
import com.app.marsrover.data.mappers.toDomain
import com.app.marsrover.data.model.RoverPhotoRemoteModel
import com.app.marsrover.data.network.Api
import com.app.marsrover.data.repository.GetMarsRoverPhotoRepositoryImpl
import com.app.marsrover.db.MarsRoverSavedPhotoDao
import com.app.marsrover.domain.repository.GetMarsRoverPhotoRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class RoverPhotoRepositoryTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()
    private lateinit var getMarsRoverPhotoRepository: GetMarsRoverPhotoRepository
    private lateinit var api: Api
    private lateinit var marsRoverSavedPhotoDao: MarsRoverSavedPhotoDao
    private val roverPhotoRemoteModel = mock<RoverPhotoRemoteModel>()
    private val roverName = "Perseverance"
    private val sol = "0"

    @Before
    fun setUp() {
        api = mock()
        marsRoverSavedPhotoDao = mock()
        getMarsRoverPhotoRepository = GetMarsRoverPhotoRepositoryImpl(api, marsRoverSavedPhotoDao)
    }

    @Test
    fun returnSuccessWhenGetDataFromAPI() = runTest {
        `when`(api.getMarsRoverPhotos(roverName, sol)).thenReturn(
            roverPhotoRemoteModel
        )
        val result = getMarsRoverPhotoRepository.getMarsRoverPhotos(roverName, sol)
        coroutineRule.testDispatcher.scheduler.advanceUntilIdle()
        Assert.assertEquals(roverPhotoRemoteModel!!.toDomain(), result)
    }


}