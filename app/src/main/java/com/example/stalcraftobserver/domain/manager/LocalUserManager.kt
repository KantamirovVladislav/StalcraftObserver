package com.example.stalcraftobserver.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {

    suspend fun saveAppEntry()

    suspend fun saveUserRegion(region: String)

    fun readAppEntry(): Flow<Boolean>

    fun readUserRegion(): Flow<String>
}