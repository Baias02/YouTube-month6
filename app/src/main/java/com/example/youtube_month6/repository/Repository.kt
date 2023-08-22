package com.example.youtube_month6.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.youtube_month6.core.network.RemoteDataSource
import com.example.youtube_month6.core.network.Resource
import com.example.youtube_month6.data.model.PlayListsModel
import kotlinx.coroutines.Dispatchers

class Repository(private val remoteDataSource: RemoteDataSource) {

    fun getPlaylistItems(id: String): LiveData<Resource<PlayListsModel>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val data = remoteDataSource.getPlaylistItems(id)
            emit(data)
        }
    }

    fun getPlayLists(): LiveData<Resource<PlayListsModel>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val data = remoteDataSource.getPlaylists()
            emit(data)
        }
    }

    fun getVideos(id: String): LiveData<Resource<PlayListsModel>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val data = remoteDataSource.getVideos(id)
            emit(data)
        }
    }
}
