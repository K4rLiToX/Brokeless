package com.carlosdiestro.brokeless.core.framework.database.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert
    suspend fun insert(entity: CategoryEntity)

    @Update
    suspend fun update(entity: CategoryEntity)

    @Query("SELECT * FROM category_table")
    fun getAll(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM category_table WHERE id = :id")
    fun getById(id: Int): Flow<CategoryEntity>
}