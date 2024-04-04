package com.app.marsrover.roverPhotos

import com.app.marsrover.MainCoroutineRule
import com.app.marsrover.data.remote.mappers.toDomain
import com.app.marsrover.data.remote.model.RoverPhotoRemoteModel
import com.app.marsrover.data.remote.network.Api
import com.app.marsrover.data.repository.GetMarsRoverPhotoRepositoryImpl
import com.app.marsrover.data.db.entity.MarsRoverSavedLocalModel
import com.app.marsrover.data.db.dao.MarsRoverSavedPhotoDao
import com.app.marsrover.domain.model.RoverPhotoUiModel
import com.app.marsrover.domain.repository.GetMarsRoverPhotoRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
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


    @Test
    fun returnSuccessWhenAddDataInDB() = runTest {
        val roverPhotoUiModel = RoverPhotoUiModel(
            id = 2,
            roverName = "Perseverance",
            imgSrc = "https://example.com/photo1",
            sol = "20",
            earthDate = "2022-07-02",
            cameraFullName = "Camera One",
            isSaved = true
        )
        val marsRoverSavedLocalModel = MarsRoverSavedLocalModel(
            roverPhotoId = 2,
            roverName = "Perseverance",
            imgSrc = "https://example.com/photo1",
            sol = "20",
            earthDate = "2022-07-02",
            cameraFullName = "Camera One"
        )

        val marsRoverSavedPhotoDao = mock(MarsRoverSavedPhotoDao::class.java)

        `when`(marsRoverSavedPhotoDao.insert(marsRoverSavedLocalModel)).thenAnswer { Unit }

        val getMarsRoverPhotoRepository =
            GetMarsRoverPhotoRepositoryImpl(api, marsRoverSavedPhotoDao)

        getMarsRoverPhotoRepository.savePhoto(roverPhotoUiModel)

        verify(marsRoverSavedPhotoDao).insert(marsRoverSavedLocalModel)

    }

    @Test
    fun returnSuccessWhenRemoveDataFromDB() = runTest {
        val roverPhotoUiModel = RoverPhotoUiModel(
            id = 2,
            roverName = "Perseverance",
            imgSrc = "https://example.com/photo1",
            sol = "20",
            earthDate = "2022-07-02",
            cameraFullName = "Camera One",
            isSaved = true
        )
        val marsRoverSavedLocalModel = MarsRoverSavedLocalModel(
            roverPhotoId = 2,
            roverName = "Perseverance",
            imgSrc = "https://example.com/photo1",
            sol = "20",
            earthDate = "2022-07-02",
            cameraFullName = "Camera One"
        )
        val marsRoverSavedPhotoDao = mock(MarsRoverSavedPhotoDao::class.java)

        `when`(marsRoverSavedPhotoDao.delete(marsRoverSavedLocalModel)).thenAnswer { Unit }

        val getMarsRoverPhotoRepository =
            GetMarsRoverPhotoRepositoryImpl(api, marsRoverSavedPhotoDao)

        getMarsRoverPhotoRepository.removePhoto(roverPhotoUiModel)

        verify(marsRoverSavedPhotoDao).delete(marsRoverSavedLocalModel)

    }

    @Test
    fun returnSuccessWhenAllDataRetriveFromDB() = runTest {

        val marsRoverSavedLocalModelList = listOf(
            MarsRoverSavedLocalModel(
                roverPhotoId = 2,
                roverName = "Perseverance",
                imgSrc = "https://example.com/photo1",
                sol = "20",
                earthDate = "2022-07-02",
                cameraFullName = "Camera One"
            ),
            MarsRoverSavedLocalModel(
                roverPhotoId = 4,
                roverName = "Perseverance",
                imgSrc = "https://example.com/photo2",
                sol = "20",
                earthDate = "2022-07-02",
                cameraFullName = "Camera Two"
            )
        )
        val marsRoverSavedPhotoDao = mock(MarsRoverSavedPhotoDao::class.java)

        `when`(marsRoverSavedPhotoDao.getAllSaved()).thenReturn(
            flowOf(marsRoverSavedLocalModelList)
        )

        val getMarsRoverPhotoRepository =
            GetMarsRoverPhotoRepositoryImpl(api, marsRoverSavedPhotoDao)

        val result = getMarsRoverPhotoRepository.getAllSavedData().first()

        val exceptedResult = listOf(
            RoverPhotoUiModel(
                id = 2,
                roverName = "Perseverance",
                imgSrc = "https://example.com/photo1",
                sol = "20",
                earthDate = "2022-07-02",
                cameraFullName = "Camera One",
                isSaved = true
            ),
            RoverPhotoUiModel(
                id = 4,
                roverName = "Perseverance",
                imgSrc = "https://example.com/photo2",
                sol = "20",
                earthDate = "2022-07-02",
                cameraFullName = "Camera Two",
                isSaved = true
            )
        )

        Assert.assertEquals(exceptedResult, result)


    }


}