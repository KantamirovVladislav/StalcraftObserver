package com.example.stalcraftobserver.domain.model

import android.app.Application
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.Navigator
import androidx.navigation.compose.rememberNavController
import com.example.stalcraftobserver.data.manager.GitHubApi
import com.example.stalcraftobserver.data.manager.ItemDataService
import com.example.stalcraftobserver.data.manager.ItemsRoomService
import com.example.stalcraftobserver.data.manager.LocalUserManagerRel
import com.example.stalcraftobserver.data.manager.RetrofitClientItem
import com.example.stalcraftobserver.data.manager.RetrofitClientItemInfo
import com.example.stalcraftobserver.data.manager.StalcraftApi
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
    ) = ItemsRoomService(context)


    @Singleton
    @Provides
    @Named("StalcraftApiService")
    fun provideStalcraftApiService() = RetrofitClientItem().instance

    @Singleton
    @Provides
    @Named("GitHubService")
    fun provideGitHubService() = RetrofitClientItemInfo().instance

    @Singleton
    @Provides
    fun provideApplication(app: Application) = app as StalcraftApplication

    @Singleton
    @Provides
    @Named("ItemDataService")
    fun provideItemDataService(
        application: StalcraftApplication,
        @Named("StalcraftApiService") stalcraftApiService: StalcraftApi,
        @Named("GitHubService") gitHubService: GitHubApi,
    ) = ItemDataService(application, gitHubApi = gitHubService, stalcraftApi = stalcraftApiService)

    @Singleton
    @Provides
    @Named("LocalUserManager")
    fun provideLocalUserManager(
        @ApplicationContext context: Context
    ) = LocalUserManagerRel(context = context)
}