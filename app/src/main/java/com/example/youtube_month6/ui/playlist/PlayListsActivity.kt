package com.example.youtube_month6.ui.playlist

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.youtube_month6.core.base.BaseActivity
import com.example.youtube_month6.core.network.Resource.Status.ERROR
import com.example.youtube_month6.core.network.Resource.Status.LOADING
import com.example.youtube_month6.core.network.Resource.Status.SUCCESS
import com.example.youtube_month6.data.model.PlayListsModel
import com.example.youtube_month6.databinding.ActivityPlaylistsBinding
import com.example.youtube_month6.internet.ConnectionInternet
import com.example.youtube_month6.ui.detail.DetailPlaylistActivity
import com.example.youtube_month6.ui.playlist.adapter.AdapterPlayLists
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayListsActivity : BaseActivity<ActivityPlaylistsBinding, PlaylistViewModel>() {

    private var adapter = AdapterPlayLists(this::onClick)
    private lateinit var connectionInternet: ConnectionInternet

    override val viewModel: PlaylistViewModel by viewModel()

    override fun inflateViewBinding() = ActivityPlaylistsBinding.inflate(layoutInflater)

    override fun setupLiveData() {
        super.setupLiveData()
        viewModel.playlists().observe(this) {
            when (it.status) {
                SUCCESS -> {
                    it.data?.let { it1 ->
                        adapter.setList(it1.items)
                    }
                    binding.recyclerView.adapter = adapter
                    Toast.makeText(this, it.data?.kind ?: "null", Toast.LENGTH_SHORT).show()
                }

                ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    Log.e("ololo", "setupLiveData: ${it.message}")
                }

                LOADING -> Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    override fun checkInternet() {
//        super.checkInternet()
//        connectionInternet = ConnectionInternet(this)
//        connectionInternet.observe(this) { isConnection ->
//            if (isConnection) {
//                binding.btnTryAgain.setOnClickListener {
//
//                    binding.noInternet.visibility = View.GONE
//                    binding.yesInternet.visibility = View.VISIBLE
//                }
//            } else {
//                binding.noInternet.visibility = View.VISIBLE
//                binding.yesInternet.visibility = View.GONE
//            }
//        }
//    }
//override fun checkInternet() {
//    super.checkInternet()
//    connectionInternet = ConnectionInternet(this)
//    connectionInternet.observe(this) { isConnection ->
//        if (isConnection) {
//            binding.btnTryAgain.setOnClickListener {
//
//                binding.noInternet.visibility = View.GONE
//                binding.yesInternet.visibility = View.VISIBLE
//            }
//        } else {
//            binding.noInternet.visibility = View.VISIBLE
//            binding.yesInternet.visibility = View.GONE
//        }
//    }
//}

    override fun checkInternet() {
        super.checkInternet()
        connectionInternet = ConnectionInternet(this)
        connectionInternet.observe(this) { isConnection ->
            if (isConnection == false) {
                binding.noInternet.visibility = View.VISIBLE
                binding.yesInternet.visibility = View.GONE
            }
            binding.btnTryAgain.setOnClickListener {
                if (isConnection) {
                    binding.noInternet.visibility = View.GONE
                    binding.yesInternet.visibility = View.VISIBLE
                } else {
                    binding.noInternet.visibility = View.VISIBLE
                    binding.yesInternet.visibility = View.GONE
                    return@setOnClickListener
                }
            }
        }
    }


    private fun onClick(item: PlayListsModel.Item) {
        val intent = Intent(this, DetailPlaylistActivity::class.java)
        intent.putExtra("title", item.snippet.title)
        intent.putExtra("desc", item.snippet.description)
        intent.putExtra("id", item.id)
        intent.putExtra("count", item.contentDetails.itemCount)
        startActivity(intent)
    }
}