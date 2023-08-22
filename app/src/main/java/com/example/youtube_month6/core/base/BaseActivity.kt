package com.example.youtube_month6.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.youtube_month6.BuildConfig

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {

    protected lateinit var binding: VB
    protected abstract fun inflateViewBinding(): VB
    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateViewBinding()
        setContentView(binding.root)

        checkInternet()
        setUI()
        setupLiveData()
        initClickListener()
    }

    open fun setupLiveData() {} // инициализация Live data

    open fun setUI() {} // инициализация UI

    open fun initClickListener() {} // внутри этого метода обрабатываем все клики

    open fun checkInternet() {} // внутри этого метода проверяем интернет

}

