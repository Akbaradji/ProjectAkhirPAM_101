package com.example.projectakhirpam.navigasi

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectakhirpam.R
import com.example.projectakhirpam.ui.halaman.BeliScreen
import com.example.projectakhirpam.ui.halaman.DestinasiBeli
import com.example.projectakhirpam.ui.halaman.DestinasiDetailPemesanan
import com.example.projectakhirpam.ui.halaman.DestinasiEdit
import com.example.projectakhirpam.ui.halaman.DestinasiHome
import com.example.projectakhirpam.ui.halaman.DestinasiInput
import com.example.projectakhirpam.ui.halaman.DetailScreen
import com.example.projectakhirpam.ui.halaman.Homepage
import com.example.projectakhirpam.ui.halaman.ItemEditScreen
import com.example.projectakhirpam.ui.halaman.PembelianScreen

@Composable
fun TopUpApp(navController: NavHostController = rememberNavController()) {
    HostNavigasi(navController = navController)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeliTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            }
        }
    )
}

@Composable
fun HostNavigasi(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ) {
        composable(DestinasiHome.route) {
            Homepage(navigateToRent = { navController.navigate(DestinasiBeli.route) })
        }
        composable(DestinasiBeli.route) {
            BeliScreen(
                navigateToItemEntry = { navController.navigate(DestinasiInput.route) },
                onDetailClick = { navController.navigate("${DestinasiDetailPemesanan.route}/$it") })
        }
        composable(DestinasiInput.route) {
            PembelianScreen(navigateBack = { navController.popBackStack() })
        }
        composable(
            DestinasiDetailPemesanan.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailPemesanan.beliIdArg) {
                type = NavType.IntType
            }
            )) {
            DetailScreen(
                navigateToEditItem = { navController.navigate("${DestinasiEdit.route}/$it") },
                navigasiBack = { navController.popBackStack() })
        }
        composable(
            DestinasiEdit.routeWithArgs,
            arguments = listOf(navArgument(DestinasiEdit.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            ItemEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.popBackStack() })
        }
    }
}