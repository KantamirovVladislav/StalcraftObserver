package com.example.stalcraftobserver.domain.model

import android.app.Application
import android.util.Log
import com.example.stalcraftobserver.data.manager.LocalUserManagerRel
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltAndroidApp
class StalcraftApplication : Application() {
    private val _localRegion = MutableStateFlow("ru")
    val localRegion = _localRegion.asStateFlow()

    private val _globalRegion = MutableStateFlow("ru")
    val globalRegion = _globalRegion.asStateFlow()

    private lateinit var userManager: LocalUserManagerRel

    override fun onCreate() {
        super.onCreate()

        userManager = LocalUserManagerRel(applicationContext)

        CoroutineScope(Dispatchers.IO).launch {
            val region = userManager.readUserRegion().first()
            _localRegion.value = region
            _globalRegion.value = if (region != "ru") "global" else "ru"
            Log.i("${this@launch}","Received regions: local region - ${_localRegion.value} | global region - ${_globalRegion.value}")
        }
    }

    //Function for update user region in global settings
    fun updateRegion(newRegion: String) {
        CoroutineScope(Dispatchers.IO).launch {
            userManager.saveUserRegion(newRegion)
            _localRegion.value = newRegion
            _globalRegion.value = if (newRegion != "ru") "global" else "ru"
            Log.i("${this@launch}", "Updates regions: local region - ${_localRegion.value} | global region - ${_globalRegion.value}")
        }
    }
}