package com.github.ebrahimi16153.mvifoodapp.util.di

import com.github.ebrahimi16153.mvifoodapp.data.server.ApiService
import com.github.ebrahimi16153.mvifoodapp.util.Constant
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApiMoule {

    @Singleton
    @Provides
    fun baseUrl(): String = Constant.BASE_URL

    @Singleton
    @Provides
    fun connectionTime(): Long = Constant.CONNECTION_TIMEOUT

    @Singleton
    @Provides
    fun provideGson() = GsonBuilder().setLenient().create()


    @Singleton
    @Provides
    fun provideInterceptor() = HttpLoggingInterceptor().apply {

        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideClient(time: Long, interceptor: HttpLoggingInterceptor) =
        OkHttpClient
            .Builder()
            .addInterceptor(interceptor = interceptor)
            .writeTimeout(time, TimeUnit.SECONDS)
            .readTimeout(time, TimeUnit.SECONDS)
            .connectTimeout(time, TimeUnit.SECONDS)
            .build()


    @Singleton
    @Provides
    fun provideApiServices(client: OkHttpClient, gson: Gson, baseUrl: String): ApiService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
            .create(ApiService::class.java)




}