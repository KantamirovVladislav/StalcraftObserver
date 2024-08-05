package com.example.stalcraftobserver.presentation.onBoarding

import androidx.annotation.DrawableRes
import com.example.stalcraftobserver.R

data class Page (
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        title = "Lorem",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        image = R.drawable.welcome_ico
    ),
    Page(
        title = "de Finibus Bonorum et Malorum",
        description = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam",
        image = R.drawable.history_ico
    ),
    Page(
        title = "Neque",
        description = "Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem.",
        image = R.drawable.settings_ico
    ),
    Page(
        title = "Ut enim",
        description = "Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? ",
        image = R.drawable.ringing_ico
    )
)