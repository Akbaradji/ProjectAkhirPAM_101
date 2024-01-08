package com.example.projectakhirpam.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface pembeliDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pembeli: pembeli)

    @Update
    suspend fun update(pembeli: pembeli)

    @Delete
    suspend fun delete(pembeli: pembeli)

    @Query("SELECT * from pembeli WHERE id = :id")
    fun getpembeli(id: Int): Flow<pembeli>

    @Query("SELECT * from pembeli ORDER BY nama ASC")
    fun getAllpembeli(): Flow<List<pembeli>>
}