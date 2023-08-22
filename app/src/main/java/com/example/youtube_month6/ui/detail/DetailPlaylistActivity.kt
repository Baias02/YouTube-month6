package com.example.youtube_month6.ui.detail

import android.content.Intent
import android.view.View
import com.example.youtube_month6.core.base.BaseActivity
import com.example.youtube_month6.core.network.Resource
import com.example.youtube_month6.data.model.PlayListsModel
import com.example.youtube_month6.databinding.ActivityDetailPlaylistBinding
import com.example.youtube_month6.internet.ConnectionInternet
import com.example.youtube_month6.ui.detail.adapter.AdapterDetailPlaylist
import com.example.youtube_month6.ui.player.PlayerActivity
import com.example.youtube_month6.ui.playlist.PlayListsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailPlaylistActivity : BaseActivity<ActivityDetailPlaylistBinding, DetailViewModel>() {

    private  var adapter = AdapterDetailPlaylist(this::onClick)
    private lateinit var connectionInternet: ConnectionInternet

    override val viewModel: DetailViewModel by viewModel()

    override fun inflateViewBinding(): ActivityDetailPlaylistBinding {
        return ActivityDetailPlaylistBinding.inflate(layoutInflater)
    }

    override fun setUI() {
        super.setUI()
        val title = intent.getStringExtra("title")
        val desc = intent.getStringExtra("desc")
        val id = intent.getStringExtra("id")
        val count = intent.getIntExtra("count", 0)

        viewModel.playlistItems(id ?: "").observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { it1 -> adapter.setList(it1.items) }
                    binding.tvTitle.text = title
                    binding.tvDesc.text = desc
                    binding.tvVideos.text = "$count video series"
                }
                Resource.Status.ERROR -> {}
                Resource.Status.LOADING -> {}
            }
        }
        binding.recyclerView.adapter = adapter
    }

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

    override fun initClickListener() {
        super.initClickListener()
        binding.backTv.setOnClickListener{
            finish()
        }
    }

    private fun onClick(item: PlayListsModel.Item?){
        val intent = Intent(this@DetailPlaylistActivity, PlayerActivity::class.java)
        intent.putExtra("id1",item?.contentDetails?.videoId)
        intent.putExtra("title1",item?.snippet?.title)
        intent.putExtra("desc1",item?.snippet?.description)
        startActivity(intent)
    }

}