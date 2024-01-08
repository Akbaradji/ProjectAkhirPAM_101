package com.example.projectakhirpam.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.projectakhirpam.data.pembeli
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

data class UIStateBeli(
    val detailPembeli : DetailPembeli = DetailPembeli(),
    val isEntryValid : Boolean = false
)

data class DetailPembeli (
    val id : Int = 0,
    val nama : String = "",
    val telpon : String = "",
    val metode: String = "",
    val harga: String = "",
)

fun DetailPembeli.toPembeli(): pembeli = pembeli(
    id = id,
    nama = nama,
    telpon = telpon,
    metode_bayar = metode,
    harga_topup = harga,
)
fun pembeli.toUiStatePembeli(isEntryValid: Boolean = false):UIStateBeli = UIStateBeli(
    detailPembeli = this.toDetailPembeli(),
    isEntryValid = isEntryValid
)

fun pembeli.toDetailPembeli():DetailPembeli = DetailPembeli(
    id = id,
    nama = nama,
    telpon = telpon,
    metode = metode_bayar ,
    harga = harga_topup,
)
