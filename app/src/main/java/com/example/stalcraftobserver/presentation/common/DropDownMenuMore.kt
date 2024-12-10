package com.example.stalcraftobserver.presentation.common

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DropdownMenuMore(
    isMenuExpanded: Boolean,
    onDismissRequest: () -> Unit,
    onMenuItemClick: (String) -> Unit
) {
    DropdownMenu(
        expanded = isMenuExpanded,
        onDismissRequest = onDismissRequest
    ) {
        DropdownMenuItem(
            text = { Text("Сравнить") },
            onClick = {
                onDismissRequest()
                onMenuItemClick("Сравнить")
            }
        )
    }
}