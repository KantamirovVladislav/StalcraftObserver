package com.example.stalcraftobserver.domain.model.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stalcraftobserver.domain.model.entities.Favorite

@Dao
interface FavoriteDao {
    @Query("select * from Favorites")
    suspend fun getFavoritesId(): List<Favorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteId(id: Favorite): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteIds(ids: List<Favorite>): List<Long>

    @Delete
    fun deleteFavoriteId(ids: List<Favorite>)
}