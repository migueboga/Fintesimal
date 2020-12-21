package com.example.fintesimal.data.network.response

data class mapResponse (
    val streetName: String,
    val suburb: String,
    val visited: Boolean,
    val location: Location
)

data class Location(
    val latitude: Double,
    val longitude: Double
)