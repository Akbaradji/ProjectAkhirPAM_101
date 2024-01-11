package com.example.projectakhirpam.model.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhirpam.model.UIStateBeli
import com.example.projectakhirpam.repositori.RepositoriGame
import com.example.projectakhirpam.ui.halaman.DestinasiEdit
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class GameEditViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoriGame: RepositoriGame
): ViewModel() {
    var gameUiState by mutableStateOf(UIStateGame())
        private set
    private val itemId : Int = checkNotNull(savedStateHandle[DestinasiEdit.itemIdArg])

    init {
        viewModelScope.launch {
            gameUiState = repositoriGame.getgameStream(itemId)
                .filterNotNull()
                .first()
                .toUiStateGame(true)
        }
    }
    suspend fun updateGame(){
        if (validasiInput(gameUiState.detailGame)){
            repositoriGame.updategame(gameUiState.detailGame.toGame())
        }
        else{
            println("Data tidak Valid")
        }
    }
    fun updateUiState (detailGame: DetailGame){
        gameUiState =
            UIStateGame(detailGame = detailGame, isEntryValid = validasiInput(detailGame))
    }
    private fun validasiInput(uiState: DetailGame = gameUiState.detailGame):Boolean{
        return with(uiState){
            nama.isNotBlank()&&harga.isNotBlank()&&harga.isNotBlank()
        }
    }
}