package com.example.stalcraftobserver.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    label: String = "",
    onDismissRequest: () -> Unit = {}
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    tint = MaterialTheme.colorScheme.error,
                    contentDescription = "Error data",
                    modifier = Modifier.scale(1.4f).padding(vertical = 8.dp)
                )
                Text(
                    text = label,
                    modifier = Modifier
                        .padding(4.dp)
                        .wrapContentSize(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
                TextButton(
                    onClick = onDismissRequest,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "Понятно")
                }
            }
        }
    }
}