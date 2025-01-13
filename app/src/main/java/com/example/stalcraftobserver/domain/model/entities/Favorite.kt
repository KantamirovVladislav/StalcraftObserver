package com.example.stalcraftobserver.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Favorites")
data class Favorite(
    @PrimaryKey val favoriteId: String,
)
