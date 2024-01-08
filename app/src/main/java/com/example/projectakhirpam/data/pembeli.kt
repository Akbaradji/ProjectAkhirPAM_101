package com.example.projectakhirpam.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pembeli")
data class pembeli(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String,
    val telpon: String,
    val metode_bayar: String,
    val harga_topup: String
)