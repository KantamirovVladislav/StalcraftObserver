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
        DropdownMenuItem(
            text = { Text("Loadout")},
            onClick ={
                onDismissRequest()
                onMenuItemClick("Loadout")
            }
        )
        DropdownMenuItem(
            text = { Text("Artefact build")},
            onClick = {
                onDismissRequest()
                onMenuItemClick("Artefact_build")
            }
        )
    }
}