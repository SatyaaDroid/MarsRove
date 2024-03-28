package com.app.marsrover.domain.use_cases

import com.app.marsrover.data.common.Resource
import com.app.marsrover.domain.model.RoverPhotoUiModel
import com.app.marsrover.domain.repository.GetMarsRoverPhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMarsRoverPhotoUseCase @Inject constructor(
    private val getMarsRoverPhotoRepository: GetMarsRoverPhotoRepository
) {

    operator fun invoke(
        roverName: String,
        sol: String
    ): Flow<Resource<List<RoverPhotoUiModel>>> = flow {

        emit(Resource.Loading())
        try {
            emit(
                Resource.Success(
                    data = getMarsRoverPhotoRepository.getMarsRoverPhotos(
                        roverName,
                        sol
                    )
                )
            )

        } catch (e: Exception) {
            emit(Resource.Error(message = e.message.toString()))
        }
    }
}