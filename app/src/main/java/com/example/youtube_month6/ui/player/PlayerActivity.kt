package com.example.youtube_month6.ui.player

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Environment
import android.view.View
import android.view.Window
import android.widget.RadioButton
import android.widget.Toast
import com.example.youtube_month6.R
import com.example.youtube_month6.core.base.BaseActivity
import com.example.youtube_month6.core.network.Resource
import com.example.youtube_month6.databinding.ActivityPlayerBinding
import com.example.youtube_month6.internet.ConnectionInternet
import com.google.android.material.button.MaterialButton
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("DEPRECATION")
class PlayerActivity : BaseActivity<ActivityPlayerBinding, PlayerViewModel>() {

    override val viewModel: PlayerViewModel by viewModel()
    private lateinit var connectionInternet: ConnectionInternet
    private val id by lazy { intent.getStringExtra("id1") }
    private val title by lazy { intent.getStringExtra("title1") }
    private var myDownload: Long = 0

    override fun inflateViewBinding(): ActivityPlayerBinding {
        return ActivityPlayerBinding.inflate(layoutInflater)
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

    override fun setupLiveData() {
        super.setupLiveData()
        val desc = intent.getStringExtra("desc1")
        viewModel.getVideos(id!!).observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let {
                        binding.tvTitle.text = title
                        binding.tvDesc.text = desc
                    }
                }

                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }

                Resource.Status.LOADING -> {
                    Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun setUI() {
        super.setUI()
        lifecycle.addObserver(binding.videoView)
        binding.videoView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                intent.getStringExtra("id1")?.let { youTubePlayer.loadVideo(it, 0f) }
            }
        })
        binding.download.setOnClickListener {
            alertDialog(this)
        }
    }

    @SuppressLint("CutPasteId")
    private fun alertDialog(context: Context) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_download)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialog.setCancelable(true)

        val btnFirst: RadioButton = dialog.findViewById(R.id.r_btn_1080p)
        val btnSecond: RadioButton = dialog.findViewById(R.id.r_btn_720p)
        val btnThird: RadioButton = dialog.findViewById(R.id.r_btn_480p)
        val btnDownload: MaterialButton = dialog.findViewById(R.id.btn_download)

        btnDownload.setOnClickListener {
            val videoId = id
            val videoUrl = "https://www.youtube.com/videos/$videoId"
            val uri = Uri.parse(videoUrl)
            val request = DownloadManager.Request(Uri.parse(uri.toString()))
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setTitle(title)
                .setAllowedOverMetered(true)
                .setVisibleInDownloadsUi(false)
            request.setDestinationInExternalPublicDir(
                Environment
                    .DIRECTORY_DOWNLOADS, "fileName.mp4"
            )
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(
                DownloadManager
                    .Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
            )

            val downloadManager: DownloadManager =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

            myDownload = downloadManager.enqueue(request)
            request.setDestinationInExternalFilesDir(this, "/file", "download.mp4")

            val br = object : BroadcastReceiver() {
                override fun onReceive(p0: Context?, p1: Intent?) {
                    val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                    if (id == myDownload) {
                        Toast.makeText(
                            applicationContext,
                            "error i don't known",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            applicationContext.registerReceiver(
                br,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            )
        }

        btnFirst.setOnCheckedChangeListener { _, _ ->
            Toast.makeText(this, "download 1080p", Toast.LENGTH_SHORT).show()
        }
        btnSecond.setOnCheckedChangeListener { _, _ ->
            Toast.makeText(this, "download 720pp", Toast.LENGTH_SHORT).show()
        }
        btnThird.setOnCheckedChangeListener { _, _ ->
            Toast.makeText(this, "download 480p", Toast.LENGTH_SHORT).show()
        }
    }

    override fun initClickListener() {
        super.initClickListener()
        binding.backTv.setOnClickListener {
            finish()
        }
        binding.download.setOnClickListener {
            alertDialog(this)
        }
    }
}