package com.example.stalcraftobserver.domain.model

import androidx.compose.runtime.Composable
import com.example.stalcraftobserver.presentation.common.ErrorDialog
import com.example.stalcraftobserver.presentation.common.WarningDialog
import com.example.stalcraftobserver.presentation.common.LoadingDialog
import com.example.stalcraftobserver.presentation.common.MinimalDialog


data class ErrorEntity (
    val errorType: ErrorsType = ErrorsType.NONE,
    val label: String,
    val onDismissRequest: () -> Unit = {}
) {
    @Composable
    fun ShowDialog() {
        when (errorType) {
            ErrorsType.NONE -> {

            }

            ErrorsType.WARNING -> {
                WarningDialog(
                    label = label,
                    onDismissRequest = onDismissRequest
                )
            }

            ErrorsType.ERROR -> {
                ErrorDialog(
                    label = label,
                    onDismissRequest = onDismissRequest
                )
            }

            ErrorsType.NOTIFICATION -> {
                MinimalDialog(
                    label = label,
                    onDismissRequest = onDismissRequest
                )
            }

            ErrorsType.LOADING -> {
                LoadingDialog(
                    label = label,
                    onDismissRequest = onDismissRequest
                )
            }

        }
    }
}

enum class ErrorsType {
    NONE,
    WARNING,
    ERROR,
    LOADING,
    NOTIFICATION
}