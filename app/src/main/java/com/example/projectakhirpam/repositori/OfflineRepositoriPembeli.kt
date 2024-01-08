package com.example.projectakhirpam.repositori

import com.example.projectakhirpam.data.pembeli
import com.example.projectakhirpam.data.pembeliDao
import kotlinx.coroutines.flow.Flow

class OfflineRepositoriPembeli (private val pembeliDao: pembeliDao):RepositoriPembeli {
    override fun getAllpembeliStream(): Flow<List<pembeli>> = pembeliDao.getAllpembeli()
    override fun getpembeliStream(id: Int): Flow<pembeli?> = pembeliDao.getpembeli(id)
    override suspend fun insertpembeli(pembeli: pembeli) = pembeliDao.insert(pembeli)
    override suspend fun deletepembeli(pembeli: pembeli) = pembeliDao.delete(pembeli)
    override suspend fun updatepembeli(pembeli: pembeli) = pembeliDao.update(pembeli)
}
