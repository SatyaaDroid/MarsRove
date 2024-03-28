package com.app.marsrover.data.repository

import com.app.marsrover.db.MarsRoverSavedPhotoDao
import com.app.marsrover.data.common.RoverPhotoUiModel
import com.app.marsrover.data.common.RoverPhotoUiState
import com.app.marsrover.data.converters.toDbModel
import com.app.marsrover.data.converters.toUiModel
import com.app.marsrover.data.network.Api
import com.app.marsrover.data.model.RoverPhotoRemoteModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MarsRoverPhotoRepo @Inject constructor(
    private val api: Api,
    private val marsRoverSavedPhotoDao: MarsRoverSavedPhotoDao
) {


    private fun getAllRemoteMarsRoverPhotos(
        roverName: String, sol: String
    ): Flow<RoverPhotoRemoteModel?> = flow {
        try {
            val networkRequest = api.getMarsRoverPhotos(
                roverName.lowercase(),
                sol.lowercase()
            )
            emit(networkRequest)

        } catch (thrwable: Throwable) {
            emit(null)
        }

    }


    fun getMarsRoverPhoto(
        roverName: String,
        sol: String
    ): Flow<RoverPhotoUiState> =
        marsRoverSavedPhotoDao.allSavedIds(sol, roverName).combine(
            getAllRemoteMarsRoverPhotos(roverName, sol)
        ) { local, remote ->
            remote?.let { roverPhotoRemoteModel ->
                RoverPhotoUiState.Success(
                    roverPhotoRemoteModel.photos.map { photo ->
                        RoverPhotoUiModel(
                            id = photo.id,
                            roverName = photo.rover.name,
                            imgSrc = photo.imgSrc,
                            sol = photo.sol.toString(),
                            earthDate = photo.earthDate,
                            cameraFullName = photo.camera.fullName,
                            isSaved = local.contains(photo.id)
                        )
                    }
                )
            } ?: run {
                RoverPhotoUiState.Error
            }
        }

    suspend fun savePhoto(roverPhotoUiModel: RoverPhotoUiModel) {
        marsRoverSavedPhotoDao.insert(toDbModel(roverPhotoUiModel))
    }

    suspend fun removePhoto(roverPhotoUiModel: RoverPhotoUiModel) {
        marsRoverSavedPhotoDao.delete(toDbModel(roverPhotoUiModel))
    }

    fun getAllSaved(): Flow<RoverPhotoUiState> =
        marsRoverSavedPhotoDao.getAllSaved().map { localModel ->
            RoverPhotoUiState.Success(toUiModel(localModel))

        }


//    fun getMarsRoverPhoto(
//        roverName: String,
//        sol: String
//    ): Flow<RoverPhotoUiState> = flow {
//        try {
//            val networkRequest = marsRoverPhotoService.getMarsRoverPhotos(
//                roverName, sol
//            )
//            networkRequest.photos.map {
//                it
//            }
//            emit(RoverPhotoUiState.Success(networkRequest.photos.map { photo ->
//                RoverPhotoUiModel(
//                    id = photo.id,
//                    roverName = photo.rover.name,
//                    imgSrc = photo.imgSrc,
//                    sol = photo.sol.toString(),
//                    earthDate = photo.earthDate,
//                    cameraFullName = photo.camera.fullName
//                )
//            }))
//
//        } catch (throwale: Throwable) {
//            throwale.message
//            emit(RoverPhotoUiState.Error)
//        }
//    }
}