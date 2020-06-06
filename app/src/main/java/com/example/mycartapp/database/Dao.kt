package com.example.mycartapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {
    @Insert
    suspend fun insertCustomer(customer: Customer): Long

    @Update
    suspend fun updateCustomer(customer: Customer): Int

    @Delete
    suspend fun deleteCustomer(customer: Customer): Int

    @Query("DELETE FROM customer_details")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM customer_details")
    fun getAllCustomers(): LiveData<List<Customer>>
}