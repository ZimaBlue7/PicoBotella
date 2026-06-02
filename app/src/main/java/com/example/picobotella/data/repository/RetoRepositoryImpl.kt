package com.example.picobotella.data.repository

import com.example.picobotella.data.local.dao.RetoDao
import com.example.picobotella.data.local.entities.RetoEntity
import com.example.picobotella.domain.repository.RetoRepository
import kotlinx.coroutines.flow.Flow

class RetoRepositoryImpl(private val retoDao: RetoDao) : RetoRepository {
    override fun getAllRetos(): Flow<List<RetoEntity>> = retoDao.getAllRetos()

    override suspend fun insertReto(reto: RetoEntity) {
        retoDao.insertReto(reto)
    }

    override suspend fun updateReto(reto: RetoEntity) {
        retoDao.updateReto(reto)
    }

    override suspend fun deleteReto(reto: RetoEntity) {
        retoDao.deleteReto(reto)
    }
}
