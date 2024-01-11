package com.example.projectakhirpam.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class game(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String,
    val harga: String,
    val genre: String,
)
