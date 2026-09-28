package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class FavoriteType {
    DEITY,
    BHAJAN,
    MANTRA,
    SUVICHAR
}

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey
    val id: String,
    val type: String, // from FavoriteType
    val title: String,
    val subtitle: String = "",
    val extraData: String = "",
    val addedAt: Long = System.currentTimeMillis()
)
