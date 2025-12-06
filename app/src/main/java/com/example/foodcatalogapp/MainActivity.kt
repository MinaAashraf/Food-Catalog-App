package com.example.foodcatalogapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.example.catalog.core.uicomponents.composables.AppBar
import com.example.catalog.core.uicomponents.theme.FoodCatalogAppTheme
import com.example.foodcatalogapp.navigation.AppNavHost
import com.example.foodcatalogapp.navigation.AppNavigationBar
import com.example.foodcatalogapp.navigation.Destination

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()
            var screenTitle by remember { mutableStateOf(Destination.CATALOG.label) }

            FoodCatalogAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.White,
                    topBar = {
                        AppBar(
                            title = stringResource(screenTitle),
                            onBackClicked = { finish() }
                        )
                    },
                    bottomBar = {
                        AppNavigationBar(
                            navController = navController,
                            onDestinationChange = { label ->
                                screenTitle = label
                            }
                        )
                    }
                ) { innerPadding ->
                    AppNavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController
                    )
                }
            }
        }
    }
}