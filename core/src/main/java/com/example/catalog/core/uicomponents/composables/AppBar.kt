package com.example.catalog.core.uicomponents.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.catalog.core.R
import com.example.catalog.core.uicomponents.theme.FoodCatalogAppTheme

/**
 * App bar composable with a title and an optional back button.
 * @param modifier Modifier to be applied to the app bar.
 * @param title Title text to be displayed in the app bar.
 * @param showBackButton Boolean flag to show or hide the back button. Default is true.
 * @param onBackClicked Lambda function to be invoked when the back button is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    title: String,
    showBackButton: Boolean = true,
    onBackClicked: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .shadow(0.dp),
        title = { Text(title) },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        painter = painterResource(R.drawable.left_arrow_icon),
                        modifier = Modifier.size(36.dp),
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun AppBarPreview() {
    FoodCatalogAppTheme {
        AppBar(
            title = "Catalog"
        )
    }
}