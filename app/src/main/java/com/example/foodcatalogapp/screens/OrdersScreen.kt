package com.example.foodcatalogapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.catalog.R
import com.example.catalog.core.uicomponents.theme.FoodCatalogAppTheme

// It will be separated module in the future
@Composable
fun OrdersScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(stringResource(R.string.no_orders_available_text))
    }
}

@Preview
@Composable
private fun OrdersScreenPreview() {
    FoodCatalogAppTheme {
        OrdersScreen()
    }
}