package com.example.projectakhirpam.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhirpam.repositori.RepositoriPembeli
import com.example.projectakhirpam.ui.halaman.DestinasiDetailPemesanan
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DetailViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoriPembeli: RepositoriPembeli
): ViewModel(){
    private val beliId: Int = checkNotNull(savedStateHandle[DestinasiDetailPemesanan.beliIdArg])
    val uiState : StateFlow<ItemDetailsUiState> =
        repositoriPembeli.getpembeliStream(beliId).filterNotNull().map {
            ItemDetailsUiState(detailPembeli = it.toDetailPembeli())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ItemDetailsUiState()
        )

    suspend fun deleteItem(){
        repositoriPembeli.deletepembeli(uiState.value.detailPembeli.toPembeli())
    }

    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class ItemDetailsUiState(
    val outOfStock : Boolean = true,
    val detailPembeli: DetailPembeli = DetailPembeli(),
)