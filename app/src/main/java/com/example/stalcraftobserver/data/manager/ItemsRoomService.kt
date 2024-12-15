package com.example.stalcraftobserver.data.manager

import android.content.Context
import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.room.Room
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.stalcraftobserver.domain.model.FunctionResult
import com.example.stalcraftobserver.domain.model.Item
import com.example.stalcraftobserver.domain.model.RoomModel
import com.example.stalcraftobserver.util.Constants

class ItemsRoomService(
    context: Context
) {

    companion object {
        @Volatile
        private var instance: RoomModel? = null

        fun getDataBase(context: Context): RoomModel {
            return instance ?: synchronized(this) {
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
            Log.i(
                Constants.SUCCES_DATABASE_TAG,
                "Successfully read data from database (${this@ItemsRoomService})"
            )
            FunctionResult.Success(items)
        } catch (e: SQLiteException) {
            Log.e(
                Constants.ERROR_DATABASE_TAG,
                "Database error: ${e.message} (${this@ItemsRoomService})"
            )
            FunctionResult.Error("Database error (${this@ItemsRoomService})")
        } catch (e: Exception) {
            Log.e(
                Constants.ERROR_DATABASE_TAG,
                "Unexpected error: ${e.message} (${this@ItemsRoomService})"
            )
            FunctionResult.Error("Unexpected error in database (${this@ItemsRoomService})")
        }
    }

    suspend fun getItemWithId(id: String): FunctionResult<Item> {
        return try {
            val item = db.ItemDao().getItemWithId(id)
            Log.i(
                Constants.SUCCES_DATABASE_TAG,
                "Successfully read data from database $item with id $id (${this@ItemsRoomService})"
            )
            FunctionResult.Success(item)
        } catch (e: SQLiteException) {
            Log.e(
                Constants.ERROR_DATABASE_TAG,
                "Database error: ${e.message} (${this@ItemsRoomService})"
            )
            FunctionResult.Error("Database error (${this@ItemsRoomService})")
        } catch (e: Exception) {
            Log.e(
                Constants.ERROR_DATABASE_TAG,
                "Unexpected error: ${e.message} (${this@ItemsRoomService})"
            )
            FunctionResult.Error("Unexpected error in database (${this@ItemsRoomService})")
        }
    }

    fun searchItemsByName(query: String, limit: Int, offset: Int): FunctionResult<List<Item>> {
        return try {
            val item = db.ItemDao().searchItemsByName(query, limit, offset)
            FunctionResult.Success(item)
        } catch (e: SQLiteException) {
            Log.e(
                Constants.ERROR_DATABASE_TAG,
                "Database error: ${e.message} (${this@ItemsRoomService})"
            )
            FunctionResult.Error("Database error (${this@ItemsRoomService})")
        } catch (e: Exception) {
            Log.e(
                Constants.ERROR_DATABASE_TAG,
                "Unexpected error: ${e.message} (${this@ItemsRoomService})"
            )
            FunctionResult.Error("Unexpected error in database (${this@ItemsRoomService})")
        }
    }

    fun getItemsPaged(limit: Int, offset: Int): FunctionResult<List<Item>> {
        return try {
            val item = db.ItemDao().getItemsPaged(limit = limit, offset = offset)
            Log.i(
                Constants.SUCCES_DATABASE_TAG,
                "Successfully read data from database with id (${this@ItemsRoomService})"
            )
            FunctionResult.Success(item)
        } catch (e: SQLiteException) {
            Log.e(
                Constants.ERROR_DATABASE_TAG,
                "Database error: ${e.message} (${this@ItemsRoomService})"
            )
            FunctionResult.Error("Database error (${this@ItemsRoomService})")
        } catch (e: Exception) {
            Log.e(
                Constants.ERROR_DATABASE_TAG,
                "Unexpected error: ${e.message} (${this@ItemsRoomService})"
            )
            FunctionResult.Error("Unexpected error in database (${this@ItemsRoomService})")
        }
    }

    suspend fun getItemsWithDynamicSort(
        query: String,
        sortColumns: List<String>,
        limit: Int,
        offset: Int,
        categoryFilters: List<String> = emptyList(),
        rarityFilters: List<String> = emptyList()
    ): FunctionResult<List<Item>> {
        return try {
            val item = db.ItemDao()
                .getItemsWithDynamicSort(
                    buildDynamicSortQuery(
                        query,
                        sortColumns,
                        limit,
                        offset,
                        categoryFilters,
                        rarityFilters
                    )
                )
            FunctionResult.Success(item)
        } catch (e: SQLiteException) {
            Log.e(
                Constants.ERROR_DATABASE_TAG,
                "Database error: ${e.message} (${this@ItemsRoomService})"
            )
            FunctionResult.Error("Database error (${this@ItemsRoomService})")
        } catch (e: Exception) {
            Log.e(
                Constants.ERROR_DATABASE_TAG,
                "Unexpected error: ${e.message} (${this@ItemsRoomService})"
            )
            FunctionResult.Error("Unexpected error in database (${this@ItemsRoomService})")
        }

    }

    private fun buildDynamicSortQuery(
        query: String,
        sortColumns: List<String>,
        limit: Int,
        offset: Int,
        categoryFilters: List<String> = emptyList(),
        rarityFilters: List<String> = emptyList()
    ): SimpleSQLiteQuery {
        val whereClauses = mutableListOf<String>()
        val args = mutableListOf<Any>()

        // Поиск по имени
        if (query.isNotEmpty()) {
            whereClauses.add("(nameEng LIKE '%' || ? || '%' OR nameRus LIKE '%' || ? || '%')")
            args.add(query)
            args.add(query)
        }

        // Фильтры по категориям с LIKE
        if (categoryFilters.isNotEmpty()) {
            whereClauses.add(categoryFilters.joinToString(" OR ") { "category LIKE '%' || ? || '%'" })
            args.addAll(categoryFilters)
        }

        // Фильтры по редкости
        if (rarityFilters.isNotEmpty()) {
            whereClauses.add("rarity IN (${rarityFilters.joinToString(", ") { "?" }})")
            args.addAll(rarityFilters)
        }

        // Базовый SQL запрос
        val sql = buildString {
            append("SELECT * FROM Map_Name_Id")
            if (whereClauses.isNotEmpty()) {
                append(" WHERE ${whereClauses.joinToString(" AND ")}")
            }
            if (sortColumns.isNotEmpty()) {
                append(" ORDER BY ${sortColumns.joinToString(", ")}")
            }
            append(" LIMIT ? OFFSET ?")
        }

        // Добавление limit и offset
        args.add(limit)
        args.add(offset)

        return SimpleSQLiteQuery(sql, args.toTypedArray())
    }




    fun closeDatabase() {
        instance?.close()
        instance = null
    }
}