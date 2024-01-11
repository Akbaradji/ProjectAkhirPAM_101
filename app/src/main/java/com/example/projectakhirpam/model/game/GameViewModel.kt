package com.example.projectakhirpam.model.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhirpam.data.game
import com.example.projectakhirpam.repositori.RepositoriGame
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class GameViewModel(private val repositoriGame: RepositoriGame): ViewModel() {
    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val gameUiState : StateFlow<GameUiState> = repositoriGame.getAllgameStream().filterNotNull()
        .map { GameUiState (listGame = it.toList()) }
        .stateIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = GameUiState()
        )

    data class GameUiState(
        val listGame: List<game> = listOf()
    )
}