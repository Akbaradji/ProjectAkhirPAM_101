package com.example.projectakhirpam.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.projectakhirpam.repositori.RepositoriPembeli

class InputViewModel (private val repositoriPembeli: RepositoriPembeli): ViewModel() {
    var uiStatePembeli by mutableStateOf(UIStateBeli())
        private set

    private fun validasiInput (uiState:DetailPembeli = uiStatePembeli.detailPembeli):Boolean{
        return with(uiState){
            nama.isNotBlank()&&telpon.isNotBlank()&&metode.isNotBlank()&&harga.isNotBlank()
        }
    }
    fun updateUiState(detailPembeli: DetailPembeli){
        uiStatePembeli =
            UIStateBeli(detailPembeli = detailPembeli, isEntryValid = validasiInput(detailPembeli))
    }

    suspend fun savePembeli(){
        if (validasiInput()){
            repositoriPembeli.insertpembeli(uiStatePembeli.detailPembeli.toPembeli())
        }
    }
}
