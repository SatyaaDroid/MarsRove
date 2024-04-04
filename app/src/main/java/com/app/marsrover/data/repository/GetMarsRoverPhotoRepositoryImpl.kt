package com.app.marsrover.data.repository

import com.app.marsrover.data.remote.converters.toDbModel
import com.app.marsrover.data.remote.converters.toUiModel
import com.app.marsrover.data.remote.mappers.toDomain
import com.app.marsrover.data.remote.network.Api
import com.app.marsrover.data.db.dao.MarsRoverSavedPhotoDao
import com.app.marsrover.domain.model.RoverPhotoUiModel
import com.app.marsrover.domain.repository.GetMarsRoverPhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMarsRoverPhotoRepositoryImpl @Inject constructor(
    private val api: Api,
    private val marsRoverSavedPhotoDao: MarsRoverSavedPhotoDao
) : GetMarsRoverPhotoRepository {


    override suspend fun getMarsRoverPhotos(
        roverName: String,
        sol: String
    ): List<RoverPhotoUiModel> {
        val roverPhotoRemoteModel = api.getMarsRoverPhotos(roverName, sol)
        return roverPhotoRemoteModel.toDomain()
    }

    override suspend fun savePhoto(roverPhotoUiModel: RoverPhotoUiModel) {
        marsRoverSavedPhotoDao.insert(toDbModel(roverPhotoUiModel))
    }

    override suspend fun removePhoto(roverPhotoUiModel: RoverPhotoUiModel) {
        marsRoverSavedPhotoDao.delete(toDbModel(roverPhotoUiModel))
    }

    override suspend fun allSavedIds(sol: String, roverName: String): Flow<List<Int>> {
      return  marsRoverSavedPhotoDao.allSavedIds(sol,roverName)
    }



    override suspend fun getAllSavedData(): Flow<List<RoverPhotoUiModel>> {
        return marsRoverSavedPhotoDao.getAllSaved().map { localModel ->
            toUiModel(localModel)
        }
    }



}