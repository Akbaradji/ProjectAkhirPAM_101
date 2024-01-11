package com.example.projectakhirpam.model.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.projectakhirpam.data.game
import com.example.projectakhirpam.repositori.RepositoriGame

class GameInputViewModel (private val repositoriGame: RepositoriGame): ViewModel() {
    var uiStateGame by mutableStateOf(UIStateGame())
        private set

    private fun validasiInput (uiState:DetailGame = uiStateGame.detailGame):Boolean{
        return with(uiState){
            nama.isNotBlank()&&harga.isNotBlank()&&genre.isNotBlank()
        }
    }
    fun updateUiState(detailGame: DetailGame){
        uiStateGame =
            UIStateGame(detailGame = detailGame, isEntryValid = validasiInput(detailGame))
    }

    suspend fun saveGame(){
        if (validasiInput()){
            repositoriGame.insertgame(uiStateGame.detailGame.toGame())
        }
    }
}

data class UIStateGame(
    val detailGame : DetailGame = DetailGame(),
    val isEntryValid : Boolean = false
)

data class DetailGame (
    val id : Int = 0,
    val nama : String = "",
    val harga : String = "",
    val genre : String = "",
)

fun DetailGame.toGame(): game = game(
    id = id,
    nama = nama,
    harga = harga,
    genre = genre,
)
fun game.toUiStateGame(isEntryValid: Boolean = false):UIStateGame = UIStateGame(
    detailGame = this.toDetailGame(),
    isEntryValid = isEntryValid
)

fun game.toDetailGame():DetailGame = DetailGame(
    id = id,
    nama = nama,
    harga = harga,
    genre = genre,
)