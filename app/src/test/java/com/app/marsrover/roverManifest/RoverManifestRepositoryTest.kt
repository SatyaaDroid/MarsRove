package com.app.marsrover.roverManifest

import com.app.marsrover.MainCoroutineRule
import com.app.marsrover.data.mappers.toDomain
import com.app.marsrover.data.model.RoverManifestRemoteModel
import com.app.marsrover.data.network.Api
import com.app.marsrover.data.repository.GetMarsManifestRepositoryImpl
import com.app.marsrover.domain.repository.GetMarsRoverManifestRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class RoverManifestRepositoryTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var getMarsRoverManifestRepository: GetMarsRoverManifestRepository
    private lateinit var api: Api
    private val roverManifestRemoteModel = mock<RoverManifestRemoteModel>()
    private val roverName = "Perseverance"

    @Before
    fun setUp() {
        api = mock()
        getMarsRoverManifestRepository = GetMarsManifestRepositoryImpl(api)
    }

    @Test
    fun returnSuccessWhenGetDataFromAPI() = runTest {
        `when`(api.getMarsRoverManifest(roverName)).thenReturn(
            roverManifestRemoteModel
        )
        val result = getMarsRoverManifestRepository.getMarsRoverManifest(roverName)
        coroutineRule.testDispatcher.scheduler.advanceUntilIdle()
        Assert.assertEquals(
            roverManifestRemoteModel.photoManifestRemoteModel.photos!!.toDomain(),
            result
        )

    }

}