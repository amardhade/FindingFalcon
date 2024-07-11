package com.gt.findingfalcon.di

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.gt.findingfalcon.repo.PlanetRepo
import com.gt.findingfalcon.repo.PlanetRepoListener
import com.gt.findingfalcon.network.ApiService
import com.gt.findingfalcon.network.NetworkMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppHiltModule {

    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    @Singleton
    fun provideNetworkMonitor(connectivityManager: ConnectivityManager) =
        NetworkMonitor(connectivityManager)

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun providesApiService(okHttpClient: OkHttpClient): ApiService {
        val gson: Gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder().baseUrl("http://google.com").addConverterFactory(
            GsonConverterFactory.create(gson)
        ).client(okHttpClient).build().create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesPlanetRepo(apiService: ApiService): PlanetRepoListener {
        return PlanetRepo(apiService)
    }
}