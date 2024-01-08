package com.example.projectakhirpam.ui.halaman

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhirpam.R
import com.example.projectakhirpam.model.DetailPembeli
import com.example.projectakhirpam.model.InputViewModel
import com.example.projectakhirpam.model.PenyediaViewModel
import com.example.projectakhirpam.model.UIStateBeli
import com.example.projectakhirpam.navigasi.BeliTopAppBar
import com.example.projectakhirpam.navigasi.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiInput : DestinasiNavigasi {
    override val route = "Input"
    override val titleRes = R.string.input_data

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PembelianScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InputViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BeliTopAppBar(
                title = stringResource(DestinasiInput.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior
            )
        }
    ){innerPadding ->
        EntryPembeliBody(
            uiStatePembeli = viewModel.uiStatePembeli,
            onPembeliValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.savePembeli()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
        )
    }
}

@Composable
fun EntryPembeliBody(
    uiStatePembeli: UIStateBeli,
    onPembeliValueChange: (DetailPembeli) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier
){
    Column (
        Modifier
            .fillMaxWidth()
            .padding(20.dp),
    ){
        FormInputPembeli(
            detailPembeli = uiStatePembeli.detailPembeli,
            onValueChange = onPembeliValueChange,
            onSaveClick = onSaveClick,
            uiStatePembeli = uiStatePembeli,
            pilihanBeli = listOf("Rp 2.500", "Rp 4.900", "Rp 9.850", "Rp 12.500","Rp 18.600","Rp 22.000","Rp 24.500","Rp 47.950","Rp 51.200","Rp 62.000"),
            onSelectionChanged = { uiStatePembeli.detailPembeli.harga })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPembeli(
    detailPembeli: DetailPembeli,
    modifier: Modifier = Modifier,
    onValueChange: (DetailPembeli) -> Unit = {},
    enabled: Boolean = true,
    pilihanBeli: List<String>,
    onSelectionChanged: (String) -> Unit,
    onSaveClick: () -> Unit,
    uiStatePembeli: UIStateBeli,
){
    Column (
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Spacer(modifier = modifier.padding(top = 24.dp))
        OutlinedTextField(
            value = detailPembeli.nama,
            onValueChange = { onValueChange(detailPembeli.copy(nama = it )) },
            label = { Text(stringResource(id = R.string.nama)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )
        OutlinedTextField(
            value = detailPembeli.telpon,
            onValueChange = { onValueChange(detailPembeli.copy(telpon = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.telpon)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )
        OutlinedTextField(
            value = detailPembeli.metode,
            onValueChange = { onValueChange(detailPembeli.copy(metode = it)) },
            label = { Text(stringResource(R.string.metode)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )
        Column(modifier.fillMaxWidth()) {
            pilihanBeli.forEach { item ->
                Row(modifier = Modifier.selectable(
                    selected = detailPembeli.harga == item,
                    onClick = {
                        onValueChange(detailPembeli.copy(harga = item))
                        onSelectionChanged(item)
                    }
                ),
                    verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = detailPembeli.harga == item,
                        onClick = {
                            onValueChange(detailPembeli.copy(harga = item))
                            onSelectionChanged(item)
                        }
                    )
                    Text(item)
                }
            }
        }
        Button(
            onClick = onSaveClick,
            enabled = uiStatePembeli.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "BELI")
        }
    }
}