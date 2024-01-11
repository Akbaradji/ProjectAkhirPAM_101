package com.example.projectakhirpam.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface gameDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(game: game)

    @Update
    suspend fun update(game: game)

    @Delete
    suspend fun delete(game: game)

    @Query("SELECT * from game WHERE id = :id")
    fun getpembeli(id: Int): Flow<game>

    @Query("SELECT * from game ORDER BY nama ASC")
    fun getAllgame(): Flow<List<game>>
}