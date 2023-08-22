package com.example.youtube_month6.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.youtube_month6.data.model.PlayListsModel
import com.example.youtube_month6.databinding.ItemVideosPlaylistBinding
import com.example.youtube_month6.ui.detail.DetailPlaylistActivity
import kotlin.reflect.KFunction0

class AdapterDetailPlaylist(private val onClick: (PlayListsModel.Item) -> Unit):
    RecyclerView.Adapter<AdapterDetailPlaylist.DetailPlayListViewHolder>() {

    private var list = arrayListOf<PlayListsModel.Item>()

    fun setList(lists: List<PlayListsModel.Item>){
        val size = list.size
        list.clear()
        list.addAll(lists)
        val newSize = lists.size
        if (size == newSize) notifyItemRangeChanged(0,newSize)
        else notifyItemRangeChanged(0,newSize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailPlayListViewHolder {
        return DetailPlayListViewHolder(
            ItemVideosPlaylistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DetailPlayListViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount() = list.size

   inner class DetailPlayListViewHolder(private val binding: ItemVideosPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: PlayListsModel.Item) {
            binding.tvTitle.text = item.snippet.title
            binding.ivPlaylistVideo.load(item.snippet.thumbnails.standard.url)
//            binding.tvTimeOfVideo.text = item.contentDetails.itemCount.toString()

            itemView.setOnClickListener {
                onClick(item)
            }
        }
    }
}