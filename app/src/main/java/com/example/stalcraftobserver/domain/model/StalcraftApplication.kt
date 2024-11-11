package com.example.stalcraftobserver.domain.model

import android.app.Application
import com.example.stalcraftobserver.data.manager.LocalUserManagerRel
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Singleton

@HiltAndroidApp
class StalcraftApplication(): Application() {

}