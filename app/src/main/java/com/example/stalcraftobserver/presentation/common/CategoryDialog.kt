package com.example.stalcraftobserver.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CategoryDialog(
    availableCategories: List<String>,
    selectedCategories: List<String>,
    onConfirm: (List<String>) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedItems by remember { mutableStateOf(selectedCategories.toMutableSet()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Выберите категории") },
        text = {
            LazyColumn {
                items(availableCategories) { category ->
                    val isSelected = selectedItems.contains(category)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (isSelected) {
                                    selectedItems.remove(category)
                                } else {
                                    selectedItems.add(category)
                                }
                            }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isSelected,
                            onCheckedChange = null // Обрабатываем клики через Row
                        )
                        Text(text = category, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(selectedItems.toList()) }) {
                Text("Применить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}