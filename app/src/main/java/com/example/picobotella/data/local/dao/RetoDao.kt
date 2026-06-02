package com.example.picobotella.data.local.dao

import androidx.room.*
import com.example.picobotella.data.local.entities.RetoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RetoDao {
    @Query("SELECT * FROM retos ORDER BY id DESC")
    fun getAllRetos(): Flow<List<RetoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReto(reto: RetoEntity)

    @Update
    suspend fun updateReto(reto: RetoEntity)

    @Delete
    suspend fun deleteReto(reto: RetoEntity)
}
