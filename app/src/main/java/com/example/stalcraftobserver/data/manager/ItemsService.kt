package com.example.stalcraftobserver.data.manager

import android.content.Context
import android.database.sqlite.SQLiteDatabaseLockedException
import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.room.Room
import com.example.stalcraftobserver.domain.model.FunctionResult
import com.example.stalcraftobserver.domain.model.Item
import com.example.stalcraftobserver.domain.model.RoomModel
import com.example.stalcraftobserver.util.Constants

class ItemsService(
    context: Context
) {

    companion object{
        @Volatile
        private var instance: RoomModel? = null

        fun getDataBase(context: Context): RoomModel{
            return instance ?: synchronized(this){
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    RoomModel::class.java, "Map_Name_Id"
                )
                    .createFromAsset("dataBase/StalcraftObsDataBase.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
    
    private val db = getDataBase(context)

    suspend fun getAllItems(): FunctionResult<List<Item>> {
        return try {
            val items = db.ItemDao().getAll()
            Log.i(Constants.SUCCES_DATABASE_TAG, "Successfully read data from database")
            FunctionResult.Success(items)
        } catch (e: SQLiteException) {
            Log.e(Constants.ERROR_DATABASE_TAG, "Database error: ${e.message}")
            FunctionResult.Error("Database error")
        } catch (e: Exception) {
            Log.e(Constants.ERROR_DATABASE_TAG, "Unexpected error: ${e.message}")
            FunctionResult.Error("Unexpected error in database")
        }
    }

    suspend fun getItemWithId(id: String): FunctionResult<Item> {
        return try {
            val items = db.ItemDao().getItemWithId(id)
            Log.i(Constants.SUCCES_DATABASE_TAG, "Successfully read data from database")
            FunctionResult.Success(items)
        } catch (e: SQLiteException) {
            Log.e(Constants.ERROR_DATABASE_TAG, "Database error: ${e.message}")
            FunctionResult.Error("Database error")
        } catch (e: Exception) {
            Log.e(Constants.ERROR_DATABASE_TAG, "Unexpected error: ${e.message}")
            FunctionResult.Error("Unexpected error in database")
        }
    }

    fun closeDatabase() {
        instance?.close()
        instance = null
    }
}