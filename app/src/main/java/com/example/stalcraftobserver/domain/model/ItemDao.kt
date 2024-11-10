package com.example.stalcraftobserver.domain.model

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ItemDao{
    @Query("select * from Map_Name_Id")
    fun getAll(): List<Item>

    @Query("select * from Map_Name_Id where id = :id")
    fun getItemWithId(id: String): Item

    @Query("SELECT * FROM Map_Name_Id LIMIT :limit OFFSET :offset")
    fun getItemsPaged(limit: Int, offset: Int): List<Item>
}