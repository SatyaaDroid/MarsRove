package com.app.marsrover.roverPhotos

import androidx.lifecycle.SavedStateHandle
import com.app.marsrover.MainCoroutineRule
import com.app.marsrover.data.common.Resource
import com.app.marsrover.domain.model.RoverPhotoUiModel
import com.app.marsrover.domain.use_cases.GetMarsRoverPhotoUseCase
import com.app.marsrover.presentation.viewmodel.photolist.RoverPhotosViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class RoverPhotoViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()
    private val getMarsRoverPhotoUseCase: GetMarsRoverPhotoUseCase = mock()
    private lateinit var roverPhotosViewModel: RoverPhotosViewModel
    private val roverPhotoUiModel = mock<List<RoverPhotoUiModel>>()
    private val roverName = "Perseverance"
    private val sol = "0"

    @Before
    fun setUp() {
        val savedStateHandle = SavedStateHandle()
        savedStateHandle.set("roverName", roverName)
        savedStateHandle.set("sol", sol)
        roverPhotosViewModel =
            RoverPhotosViewModel(
                getMarsRoverPhotoUseCase,
                savedStateHandle,
                coroutineRule.testDispatcher
            )
    }

    @Test
    fun returnSuccessWhenUserSeeProgressBarInit() = runTest {

        Mockito.`when`(getMarsRoverPhotoUseCase.invoke(roverName, sol)).thenReturn(
            flow {
                emit(Resource.Loading())
            }
        )
        coroutineRule.testDispatcher.scheduler.advanceUntilIdle()
        Assert.assertEquals(true, roverPhotosViewModel.roverPhotosStateHolder.value.isLoading)
    }


    @Test
    fun returnSuccessWhenStateStoreData() = runTest {

        Mockito.`when`(getMarsRoverPhotoUseCase.invoke(roverName, sol)).thenReturn(
            flow {
                emit(Resource.Success(roverPhotoUiModel))
            }
        )
        coroutineRule.testDispatcher.scheduler.advanceUntilIdle()
        Assert.assertEquals(
            roverPhotoUiModel,
            roverPhotosViewModel.roverPhotosStateHolder.value.data
        )
    }

    @Test
    fun returnErrorInErrorCase() = runTest {
        Mockito.`when`(getMarsRoverPhotoUseCase.invoke(roverName, sol)).thenReturn(
            flow {
                emit(Resource.Error("Something went wrong"))
            }
        )
        coroutineRule.testDispatcher.scheduler.advanceUntilIdle()
        Assert.assertEquals(
            "Something went wrong",
            roverPhotosViewModel.roverPhotosStateHolder.value.error
        )
    }

}