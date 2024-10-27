package com.example.stalcraftobserver.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Map_Name_Id")
data class Item(
    @PrimaryKey val id: String,
    @ColumnInfo("nameEng") val titleEng: String,
    @ColumnInfo("nameRus") val titleRus: String,
    @ColumnInfo("category") val category: String,
    @ColumnInfo("rarity") val rarity: String,
) {
    fun createImagePath(region: String) =
        "https://github.com/EXBO-Studio/stalcraft-database/raw/main/$region/icons/$category/$id.png"
}
