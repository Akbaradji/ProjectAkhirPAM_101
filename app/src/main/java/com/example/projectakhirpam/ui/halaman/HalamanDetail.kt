package com.example.projectakhirpam.ui.halaman

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhirpam.R
import com.example.projectakhirpam.data.pembeli
import com.example.projectakhirpam.model.DetailViewModel
import com.example.projectakhirpam.model.ItemDetailsUiState
import com.example.projectakhirpam.model.PenyediaViewModel
import com.example.projectakhirpam.model.toPembeli
import com.example.projectakhirpam.navigasi.BeliTopAppBar
import com.example.projectakhirpam.navigasi.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiDetailPemesanan : DestinasiNavigasi {
    override val route= "item_details"
    override val titleRes= R.string.detail_beli
    const val beliIdArg = "itemId"
    val routeWithArgs = "$route/{$beliIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navigateToEditItem : (Int) -> Unit,
    navigasiBack : ()->Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold (
        topBar = {
            BeliTopAppBar(title = stringResource(DestinasiDetailPemesanan.titleRes),
                canNavigateBack = true,
                navigateUp = navigasiBack
            )
        }, floatingActionButton = {
            FloatingActionButton(onClick = {navigateToEditItem(uiState.value.detailPembeli.id)},
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_beli)
                )
            }
        }, modifier = modifier
    ){innerPadding ->
        ItemDetailBody(
            itemDetailUiState = uiState.value,
            onDelete = {
                coroutineScope.launch {
                    viewModel.deleteItem()
                    navigasiBack()
                }
            },
            modifier= Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
private fun ItemDetailBody(
    itemDetailUiState: ItemDetailsUiState,
    onDelete: () -> Unit,
    modifier: Modifier
){
    Column (
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
        ItemDetail(
            pembeli = itemDetailUiState.detailPembeli.toPembeli(),modifier = Modifier.fillMaxWidth()
        )
        OutlinedButton(onClick =  { deleteConfirmationRequired = true},
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(stringResource(R.string.delete))
        }
        if (deleteConfirmationRequired){
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = {deleteConfirmationRequired = false},
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun ItemDetail(
    pembeli: pembeli, modifier: Modifier =Modifier
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ItemDetailRow(
                labelResId = R.string.nama,
                itemDetail = pembeli.nama,
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            )
            ItemDetailRow(
                labelResId = R.string.telpon,
                itemDetail = pembeli.telpon,
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            )
            ItemDetailRow(
                labelResId = R.string.metode,
                itemDetail = pembeli.metode_bayar,
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            )
            ItemDetailRow(
                labelResId = R.string.harga,
                itemDetail = pembeli.harga_topup,
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            )
        }
    }
}

@Composable
private fun ItemDetailRow(
    @StringRes labelResId : Int, itemDetail:String, modifier: Modifier = Modifier
){
    Row (modifier = modifier){
        Text(text = stringResource(labelResId))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = itemDetail, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm : () ->Unit,onDeleteCancel : ()->Unit, modifier: Modifier = Modifier
){
    AlertDialog(onDismissRequest = { /*DoNothing*/ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick =  onDeleteCancel) {
                Text(text = stringResource(R.string.no))

            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        }
    )
}