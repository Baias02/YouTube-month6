package com.example.youtube_month6.ui.playlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.youtube_month6.data.model.PlayListsModel
import com.example.youtube_month6.databinding.ItemPlaylistsBinding

class AdapterPlayLists(private val onCLick: (PlayListsModel.Item) -> Unit) :
    RecyclerView.Adapter<AdapterPlayLists.PlayListsViewHolder>() {

    private var list = arrayListOf<PlayListsModel.Item>()

    fun setList(lists: List<PlayListsModel.Item>) {
        val previousSize = list.size
        list.clear()
        list.addAll(lists)
        val newSize = list.size
        if (previousSize == newSize) notifyItemRangeChanged(0, newSize)
        else notifyItemRangeChanged(0, newSize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListsViewHolder {
        return PlayListsViewHolder(
            ItemPlaylistsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlayListsViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount() = list.size

    inner class PlayListsViewHolder(private val binding: ItemPlaylistsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: PlayListsModel.Item) {
            binding.ivPlaylist.load(item?.snippet?.thumbnails?.standard?.url)
            binding.tvTitle.text = item.snippet.title
            binding.tvVideoSeries.text = "${item.contentDetails.itemCount} video"

            itemView.setOnClickListener {
                onCLick(item)
            }
        }
    }
}