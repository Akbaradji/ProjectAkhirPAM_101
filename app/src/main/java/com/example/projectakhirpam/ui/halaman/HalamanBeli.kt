package com.example.projectakhirpam.ui.halaman

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhirpam.R
import com.example.projectakhirpam.data.pembeli
import com.example.projectakhirpam.model.PembeliViewModel
import com.example.projectakhirpam.model.PenyediaViewModel
import com.example.projectakhirpam.navigasi.BeliTopAppBar
import com.example.projectakhirpam.navigasi.DestinasiNavigasi

object DestinasiBeli : DestinasiNavigasi {
    override val route = "beli"
    override val titleRes = R.string.beli
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeliScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PembeliViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onDetailClick: (Int)->Unit={},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BeliTopAppBar(
                title = stringResource(DestinasiBeli.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.input)
                )
            }
        },
    ) { innerPadding ->
        val uiStatePembeli by viewModel.beliUiState.collectAsState()
        BodyPembeli(
            itemPembeli = uiStatePembeli.listPembeli,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            onDetailClick = onDetailClick
        )
    }
}

@Composable
fun BodyPembeli(
    itemPembeli: List<pembeli>,
    modifier: Modifier = Modifier,
    onDetailClick: (Int)->Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Card(
            //shape = MaterialTheme.shapes.medium,
            shape = RoundedCornerShape(8.dp),
            // modifier = modifier.size(280.dp, 240.dp)
            modifier = Modifier.padding(10.dp,5.dp,10.dp,10.dp),
            //set card elevation of the card
            elevation = CardDefaults.cardElevation(
                defaultElevation =  10.dp,
            ),
            colors = CardDefaults.cardColors(
                containerColor =  MaterialTheme.colorScheme.primaryContainer,
            ),
        ) {
            Column(modifier = Modifier.clickable(onClick = {  })) {

                Image(
                    painter = painterResource(R.drawable.baner),
                    contentDescription = null, // decorative
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Gaming",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Silahkan Beli Diamond Murah Meriah Hanya Di Toko Kami",
                        //maxLines = 1,
                        //overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
            }
        }
        if (itemPembeli.isEmpty()) {
            Text(
                text = stringResource(id = R.string.desc),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
            )
        } else {
            ListPembeli(
                itemPembeli = itemPembeli,
                modifier = Modifier.padding(horizontal = 8.dp),
                onItemClick = {onDetailClick(it.id)}
            )
        }
    }
}

@Composable
fun ListPembeli(
    itemPembeli: List<pembeli>,
    modifier: Modifier = Modifier,
    onItemClick:(pembeli) ->Unit
) {
    LazyColumn(modifier = Modifier) {
        items(items = itemPembeli, key = { it.id })
        { buy ->
            DataPembeli(
                pembeli = buy,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onItemClick(buy) }
            )
        }
    }
}


@Composable
fun DataPembeli(
    pembeli: pembeli,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Nama : " + pembeli.nama,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = "No HP : " + pembeli.telpon,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}
