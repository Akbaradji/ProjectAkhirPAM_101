package com.example.projectakhirpam.repositori

import com.example.projectakhirpam.data.game
import kotlinx.coroutines.flow.Flow

interface RepositoriGame {
    fun getAllgameStream(): Flow<List<game>>

    fun getgameStream(id:  Int): Flow<game?>

    suspend fun insertgame(game: game)
    suspend fun deletegame(game: game)
    suspend fun updategame(game: game)
}