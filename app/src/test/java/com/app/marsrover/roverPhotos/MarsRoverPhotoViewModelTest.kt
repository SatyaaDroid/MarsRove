//package com.app.marsrover.roverPhotos
//
//import com.app.marsrover.MainCoroutineRule
//import com.app.marsrover.data.repository.MarsRoverPhotoRepo
//import com.app.marsrover.data.common.RoverPhotoUiModel
//import com.app.marsrover.data.common.RoverPhotoUiState
//import com.app.marsrover.presentation.viewmodel.photolist.MarsRoverPhotoViewModel
//import io.mockk.coEvery
//import io.mockk.coVerify
//import io.mockk.mockkClass
//import junit.framework.TestCase.assertEquals
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.test.runTest
//import org.junit.Rule
//import org.junit.Test
//
//class MarsRoverPhotoViewModelTest {
//
//    private val marsRoverPhotoRepo = mockkClass(MarsRoverPhotoRepo::class)
//
//    @get:Rule
//    val coroutineRule = MainCoroutineRule()
//
//    @Test
//    fun `should emit success when repo emit success`() = runTest(coroutineRule.testDispatcher) {
//        //Given
//        val expectedResult = RoverPhotoUiState.Success(
//            roverPhotoUiModelList = listOf(
//                RoverPhotoUiModel(
//                    id = 1,
//                    roverName = "perseverance",
//                    imgSrc = "https://example.com/photo1",
//                    sol = "0",
//                    earthDate = "2020-03-10",
//                    cameraFullName = "Camera One",
//                    isSaved = true
//                ),
//                RoverPhotoUiModel(
//                    id = 2,
//                    roverName = "perseverance",
//                    imgSrc = "https://example.com/photo2",
//                    sol = "0",
//                    earthDate = "2020-03-10",
//                    cameraFullName = "Camera One",
//                    isSaved = false
//                ),
//
//                )
//        )
//
//        coEvery {
//            marsRoverPhotoRepo.getMarsRoverPhoto("perseverance", "0")
//        } returns flowOf(expectedResult)
//
//        //When
//        val marsRoverPhotoViewModel =
//            MarsRoverPhotoViewModel(marsRoverPhotoRepo, coroutineRule.testDispatcher)
//        marsRoverPhotoViewModel.getMarsRoverPhoto("perseverance", "0")
//        val result = marsRoverPhotoViewModel.roverPhotoUiState.first()
//
//        //Then
//
//        assertEquals(expectedResult, result)
//    }
//
//
//    @Test
//    fun `should emit error when repo emit error`() = runTest(coroutineRule.testDispatcher) {
//        //Given
//        coEvery {
//            marsRoverPhotoRepo.getMarsRoverPhoto("perseverance", "0")
//        } returns flowOf(RoverPhotoUiState.Error)
//
//        //When
//        val marsRoverPhotoViewModel =
//            MarsRoverPhotoViewModel(marsRoverPhotoRepo, coroutineRule.testDispatcher)
//        marsRoverPhotoViewModel.getMarsRoverPhoto("perseverance", "0")
//        val result = marsRoverPhotoViewModel.roverPhotoUiState.first()
//
//        //Then
//        assertEquals(RoverPhotoUiState.Error, result)
//    }
//
//    @Test
//    fun `should remove photo when photo is saved and change save status is called`() =
//        runTest(coroutineRule.testDispatcher) {
//            //Given
//            val roverPhotoUiModel = RoverPhotoUiModel(
//                id = 1,
//                roverName = "perseverance",
//                imgSrc = "https://example.com/photo1",
//                sol = "0",
//                earthDate = "2022-03-10",
//                cameraFullName = "Camera One",
//                isSaved = true
//            )
//            coEvery {
//                marsRoverPhotoRepo.removePhoto(roverPhotoUiModel)
//            } returns Unit
//
//            //When
//            val marsRoverPhotoViewModel = MarsRoverPhotoViewModel(marsRoverPhotoRepo,coroutineRule.testDispatcher)
//            marsRoverPhotoViewModel.changeSaveStatus(roverPhotoUiModel)
//
//            //Then
//            coVerify {
//                marsRoverPhotoRepo.removePhoto(roverPhotoUiModel)
//            }
//        }
//
//
//    @Test
//    fun `should save photo when photo is not saved and change save status is called`() =
//        runTest(coroutineRule.testDispatcher) {
//            //Given
//            val roverPhotoUiModel = RoverPhotoUiModel(
//                id = 1,
//                roverName = "perseverance",
//                imgSrc = "https://example.com/photo1",
//                sol = "0",
//                earthDate = "2022-03-10",
//                cameraFullName = "Camera One",
//                isSaved = false
//            )
//            coEvery {
//                marsRoverPhotoRepo.savePhoto(roverPhotoUiModel)
//            } returns Unit
//
//            //When
//            val marsRoverPhotoViewModel = MarsRoverPhotoViewModel(marsRoverPhotoRepo,coroutineRule.testDispatcher)
//            marsRoverPhotoViewModel.changeSaveStatus(roverPhotoUiModel)
//
//            //Then
//            coVerify {
//                marsRoverPhotoRepo.savePhoto(roverPhotoUiModel)
//            }
//        }
//}