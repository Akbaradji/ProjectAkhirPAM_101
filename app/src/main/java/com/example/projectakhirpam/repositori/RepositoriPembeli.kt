package com.example.projectakhirpam.repositori

import com.example.projectakhirpam.data.pembeli
import kotlinx.coroutines.flow.Flow

interface RepositoriPembeli {
    fun getAllpembeliStream(): Flow<List<pembeli>>

    fun getpembeliStream(id: Int): Flow<pembeli?>

    suspend fun insertpembeli(pembeli: pembeli)
    suspend fun deletepembeli(pembeli: pembeli)
    suspend fun updatepembeli(pembeli: pembeli)
}