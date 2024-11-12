package com.example.stalcraftobserver.domain.model

import android.content.Context
import com.example.stalcraftobserver.data.manager.ItemsService
import com.example.stalcraftobserver.data.manager.RetrofitClientItem
import com.example.stalcraftobserver.data.manager.RetrofitClientItemInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Singleton
    @Provides
    @Named("RoomDataService")
    fun provideRoomDataService(
        @ApplicationContext context: Context
    ) = ItemsService(context)


    @Singleton
    @Provides
    @Named("StalcraftApiService")
    fun provideStalcraftApiService(
    ) = RetrofitClientItem()

    @Singleton
    @Provides
    @Named("GitHubService")
    fun provideGitHubService() = RetrofitClientItemInfo()

}