package com.example.stalcraftobserver.domain.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 2)
abstract class RoomModel: RoomDatabase() {
    abstract fun ItemDao(): ItemDao
}