package com.example.youtube_month6.ui.playlist

import androidx.lifecycle.LiveData
import com.example.youtube_month6.core.base.BaseViewModel
import com.example.youtube_month6.core.network.Resource
import com.example.youtube_month6.data.model.PlayListsModel
import com.example.youtube_month6.repository.Repository

class PlaylistViewModel(private val repository: Repository) : BaseViewModel() {

    fun playlists(): LiveData<Resource<PlayListsModel>> {
        return repository.getPlayLists()
    }
}