package com.example.stalcraftobserver.domain.model

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface ItemDao{
    @Query("select * from Map_Name_Id")
    fun getAll(): List<Item>

    @Query("select * from Map_Name_Id where id = :id")
    fun getItemWithId(id: String): Item

    @Query("SELECT * FROM Map_Name_Id LIMIT :limit OFFSET :offset")
    fun getItemsPaged(limit: Int, offset: Int): List<Item>

    @Query("""
        SELECT * FROM Map_Name_Id 
        WHERE (nameEng LIKE '%' || :query || '%' OR nameRus LIKE '%' || :query || '%') 
        LIMIT :limit OFFSET :offset
    """)
    fun searchItemsByName(query: String, limit: Int, offset: Int): List<Item>

    @Query("""
        SELECT * FROM Map_Name_Id 
        WHERE (nameEng LIKE '%' || :query || '%' OR nameRus LIKE '%' || :query || '%')
        ORDER BY :sortOrder
        LIMIT :limit OFFSET :offset
    """)
    fun getItemsWithSorting(query: String, sortOrder: String, limit: Int, offset: Int): List<Item>

    @RawQuery
    fun getItemsWithDynamicSort(query: SupportSQLiteQuery): List<Item>
}