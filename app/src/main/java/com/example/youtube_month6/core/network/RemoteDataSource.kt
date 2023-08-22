package com.example.youtube_month6.core.network

import com.example.youtube_month6.BuildConfig
import com.example.youtube_month6.core.base.BaseDataSource
import com.example.youtube_month6.data.remote.ApiService
import com.example.youtube_month6.utils.Constants

class RemoteDataSource(private val apiService: ApiService) : BaseDataSource() {

    suspend fun getPlaylists() = getResult {
        apiService.getPlaylists(
            part = Constants.PART,
            channelId = Constants.CHANNEL_ID,
            apiKey = BuildConfig.API_KEY,
            maxResults = 20
        )
    }

    suspend fun getPlaylistItems(id: String) = getResult {
        apiService.getPlaylistItem(
            key = BuildConfig.API_KEY,
            part = Constants.PART,
            playlistId = id,
            maxResults = 100
        )
    }

    suspend fun getVideos(id: String) = getResult {
        apiService.getVideos(
            key = BuildConfig.API_KEY,
            part = Constants.PART,
            id
        )
    }
}