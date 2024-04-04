package com.app.marsrover.domain.use_cases

import com.app.marsrover.core.common.Resource
import com.app.marsrover.domain.model.RoverPhotoUiModel
import com.app.marsrover.domain.repository.GetMarsRoverPhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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
            val localIds =
                getMarsRoverPhotoRepository.allSavedIds(sol, roverName).first()
            val remoteData = getMarsRoverPhotoRepository.getMarsRoverPhotos(roverName, sol)

            val uiSateList = remoteData.map {
                RoverPhotoUiModel(
                    id = it.id,
                    roverName = it.roverName,
                    imgSrc = it.imgSrc,
                    sol = it.sol,
                    earthDate = it.earthDate,
                    cameraFullName = it.cameraFullName,
                    isSaved = localIds.contains(it.id)
                )
            }
            emit(Resource.Success(data = uiSateList))

        } catch (e: Exception) {
            emit(Resource.Error(message = e.message.toString()))
        }
    }


    suspend fun savePhoto(roverPhotoUiModel: RoverPhotoUiModel) {
        getMarsRoverPhotoRepository.savePhoto(roverPhotoUiModel)
    }

    suspend fun removePhoto(roverPhotoUiModel: RoverPhotoUiModel) {
        getMarsRoverPhotoRepository.removePhoto(roverPhotoUiModel)
    }

    suspend fun getAllSavedData(): Flow<List<RoverPhotoUiModel>> {
        return getMarsRoverPhotoRepository.getAllSavedData()
    }


}