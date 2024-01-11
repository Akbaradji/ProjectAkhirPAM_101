package com.example.projectakhirpam.repositori

import com.example.projectakhirpam.data.game
import com.example.projectakhirpam.data.gameDao
import kotlinx.coroutines.flow.Flow

class OfflineRepositoriGame (private val gameDao: gameDao):RepositoriGame {
    override fun getAllgameStream(): Flow<List<game>> = gameDao.getAllgame()

    override fun getgameStream(id: Int): Flow<game?> = gameDao.getgame(id)

    override suspend fun insertgame(game: game) = gameDao.insert(game)

    override suspend fun deletegame(game: game) = gameDao.delete(game)
    override suspend fun updategame(game: game) = gameDao.update(game)
}