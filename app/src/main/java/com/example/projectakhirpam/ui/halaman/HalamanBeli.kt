package com.example.projectakhirpam.ui.halaman

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhirpam.R
import com.example.projectakhirpam.model.PembeliViewModel
import com.example.projectakhirpam.model.PenyediaViewModel

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
