package com.example.foodcatalogapp.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController

@Composable
fun AppNavigationBar(
    navController: NavHostController,
    onDestinationChange: (Int) -> Unit
) {
    val startDestination = Destination.CATALOG.route
    var selectedDestination by remember { mutableStateOf(startDestination) }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Destination.entries.forEach { destination ->
            NavigationBarItem(
                selected = selectedDestination == destination.route,
                onClick = {
                    selectedDestination = destination.route
                    navController.navigate(destination.route)
                    onDestinationChange(destination.label)
                },
                icon = {
                    Icon(
                        painter = painterResource(destination.icon),
                        contentDescription = null
                    )
                },
                label = { Text(text = stringResource(destination.label)) },
            )
        }
    }
}