package com.example.fintesimal.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fintesimal.data.db.entities.Address

@Dao
interface AddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(address: List<Address>)

    @Query("SELECT * FROM address")
    fun getAddress(): List<Address>

    @Query("SELECT * FROM address WHERE id = :id")
    fun getAddressById(id: Int): Address

    @Query("DELETE FROM address")
    fun deleteAll()
}