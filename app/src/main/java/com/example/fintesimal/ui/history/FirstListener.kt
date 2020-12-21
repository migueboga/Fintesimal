package com.example.fintesimal.ui.history

import com.example.fintesimal.data.db.entities.Address

interface FirstListener {
    fun onVisitCalculate(visits: Int)
    fun onSuccess(arrayOfAddress: List<Address>)
    fun onFailure()
}