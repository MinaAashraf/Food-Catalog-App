package com.example.catalog.core.uicomponents.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * A simple error screen that displays an error message centered on the screen.
 *
 * @param message The error message to display.
 */
@Composable
fun ErrorScreen(message: String) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) { Text(message) }
}