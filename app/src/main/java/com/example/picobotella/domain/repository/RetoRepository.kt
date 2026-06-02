package com.example.picobotella.domain.repository

import com.example.picobotella.data.local.entities.RetoEntity
import kotlinx.coroutines.flow.Flow

interface RetoRepository {
    fun getAllRetos(): Flow<List<RetoEntity>>
    suspend fun insertReto(reto: RetoEntity)
    suspend fun updateReto(reto: RetoEntity)
    suspend fun deleteReto(reto: RetoEntity)
}
