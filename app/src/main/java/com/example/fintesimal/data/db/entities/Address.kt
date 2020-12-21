package com.example.fintesimal.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Address (
    val streetName: String,
    val suburb: String,
    var visited: Boolean,
    val latitude: Double,
    val longitude: Double
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}