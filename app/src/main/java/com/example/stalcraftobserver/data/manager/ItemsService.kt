package com.example.stalcraftobserver.data.manager

import android.content.Context
import androidx.room.Room
import com.example.stalcraftobserver.domain.model.RoomModel

class ItemsService(
    context: Context
) {
    val db = Room.databaseBuilder(
        context,
        RoomModel::class.java, "Map_Name_Id"
    ).createFromAsset("dataBase/StalcraftObsDataBase.db")
        .fallbackToDestructiveMigration()
        .build()
}