package com.example.projectakhirpam.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhirpam.repositori.RepositoriPembeli
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoriPembeli: RepositoriPembeli
): ViewModel() {
    var pembeliUiState by mutableStateOf(UIStateBeli())
        private set
    private val itemId : Int = checkNotNull(savedStateHandle[DestinasiEdit.itemIdArg])

    init {
        viewModelScope.launch {
            pembeliUiState = repositoriPembeli.getpembeliStream(itemId)
                .filterNot()
                .first()
                .toUiStatePembeli(true)
        }
    }
    suspend fun updateSiswa(){
        if (validasiInput(pembeliUiState.detailPembeli)){
            repositoriPembeli.updatepembeli(pembeliUiState.detailPembeli.toPembeli())
        }
        else{
            println("Data tidak Valid")
        }
    }
    fun updateUiState (detailPembeli: DetailPembeli){
        pembeliUiState =
            UIStateBeli(detailPembeli = detailPembeli, isEntryValid = validasiInput(detailPembeli))
    }
    private fun validasiInput(uiState: DetailPembeli = pembeliUiState.detailPembeli):Boolean{
        return with(uiState){
            nama.isNotBlank()&&telpon.isNotBlank()&&metode.isNotBlank()&&harga.isNotBlank()
        }
    }
}