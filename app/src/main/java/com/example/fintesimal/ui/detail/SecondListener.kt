package com.example.fintesimal.ui.detail

import com.example.fintesimal.data.db.entities.Address

interface SecondListener {
    fun onSuccess(address: Address)
    fun onFailure()
    fun onGoLocation(address: Address)
}