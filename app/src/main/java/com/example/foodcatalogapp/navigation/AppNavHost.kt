package com.example.foodcatalogapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.foodcatalogapp.catalog.presentation.ProductCatalogScreen
import com.example.foodcatalogapp.screens.MenuScreen
import com.example.foodcatalogapp.screens.OrdersScreen
import com.example.foodcatalogapp.screens.SettingsScreen

@Composable
fun AppNavHost(
    modifier: Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destination.CATALOG.route
    ) {
        composable(Destination.CATALOG.route) {
            ProductCatalogScreen()
        }

        composable(Destination.ORDERS.route) {
            OrdersScreen()
        }

        composable(Destination.MENU.route) {
            MenuScreen()
        }

        composable(Destination.SETTINGS.route) {
            SettingsScreen()
        }
    }
}