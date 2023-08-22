package com.example.youtube_month6.core.network

import com.example.youtube_month6.data.remote.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideInterceptor() }
    factory { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    single { provideApi(get()) }
}

fun provideInterceptor() = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)

fun provideOkHttpClient(interceptor: HttpLoggingInterceptor) = OkHttpClient().newBuilder()
    .connectTimeout(20, TimeUnit.SECONDS)
    .writeTimeout(20, TimeUnit.SECONDS)
    .readTimeout(20, TimeUnit.SECONDS)
    .addInterceptor(interceptor)
    .build()

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(com.example.youtube_month6.BuildConfig.BASE_URL)
    .client(okHttpClient)
    .build()

fun provideApi(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}
