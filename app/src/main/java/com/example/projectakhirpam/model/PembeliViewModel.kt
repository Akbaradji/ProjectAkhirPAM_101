package com.example.projectakhirpam.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhirpam.data.pembeli
import com.example.projectakhirpam.repositori.RepositoriPembeli
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PembeliViewModel (private val repositoriPembeli: RepositoriPembeli): ViewModel(){
    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val beliUiState : StateFlow<BeliUiState> = repositoriPembeli.getAllpembeliStream().filterNotNull()
        .map { BeliUiState(listPembeli = it.toList()) }
        .stateIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = BeliUiState()

            )
    data class BeliUiState(
        val listPembeli: List<pembeli> = listOf()
    )
}