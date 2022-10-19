package com.carlosdiestro.brokeless.core.framework.database.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert
    suspend fun insert(entity: CategoryEntity)

    @Query("SELECT * FROM category_table")
    fun getAll(): Flow<List<CategoryEntity>>
}