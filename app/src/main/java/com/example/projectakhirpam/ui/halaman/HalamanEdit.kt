package com.example.projectakhirpam.ui.halaman

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhirpam.R
import com.example.projectakhirpam.model.EditViewModel
import com.example.projectakhirpam.model.PenyediaViewModel
import com.example.projectakhirpam.navigasi.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiEdit: DestinasiNavigasi {
    override val route = "edit"
    override val titleRes = R.string.edit
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    Scaffold (
        topBar = {
            BeliTopAppBar(
                title = stringResource(DestinasiEdit.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ){innerPadding ->
        EntryPembeliBody(
            uiStatePembeli = viewModel.pembeliUiState,
            onPembeliValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePembeli()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}