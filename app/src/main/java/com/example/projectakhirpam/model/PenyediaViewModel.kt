package com.example.projectakhirpam.model

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            PembeliViewModel(topup().container.repositoriPembeli)
        }
        initializer {
            InputViewModel(topup().container.repositoriPembeli)
        }
        initializer {
            DetailViewModel(createSavedStateHandle(),
                topup().container.repositoriPembeli
            )
        }
        initializer {
            EditViewModel(createSavedStateHandle(),
                topup().container.repositoriPembeli
            )
        }
    }
}

fun CreationExtras.topup():TopUpApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as TopUpApp)