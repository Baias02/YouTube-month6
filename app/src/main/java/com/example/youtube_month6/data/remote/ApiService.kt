package com.example.youtube_month6.data.remote

import android.provider.MediaStore.Video
import com.example.youtube_month6.data.model.PlayListsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("playlists")
    suspend fun getPlaylists(
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("key") apiKey: String,
        @Query("maxResults") maxResults: Int = 20
    ): Response<PlayListsModel>

    @GET("playlistItems")
    suspend fun getPlaylistItem(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("playlistId") playlistId: String,
        @Query("maxResults") maxResults: Int = 20
    ): Response<PlayListsModel>

    @GET("videos")
    suspend fun getVideos(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("id") id: String
    ): Response<PlayListsModel>

}