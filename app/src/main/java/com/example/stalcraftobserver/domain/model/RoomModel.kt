package com.example.stalcraftobserver.domain.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.stalcraftobserver.domain.model.daos.FavoriteDao
import com.example.stalcraftobserver.domain.model.daos.ItemDao
import com.example.stalcraftobserver.domain.model.entities.Favorite
import com.example.stalcraftobserver.domain.model.entities.Item

@Database(entities = [Item::class, Favorite::class], version = 3)
abstract class RoomModel: RoomDatabase() {
    abstract fun ItemDao(): ItemDao
    abstract fun FavoriteDao(): FavoriteDao
}