package com.example.mycartapp.repository

import com.example.mycartapp.database.Customer
import com.example.mycartapp.database.Dao

class CustomerRepository(private val dao: Dao) {
    val customer = dao.getAllCustomers()
    suspend fun insert(customer: Customer): Long {
        return dao.insertCustomer(customer)
    }

    suspend fun update(customer: Customer): Int {
        return dao.updateCustomer(customer)
    }

    suspend fun delete(customer: Customer): Int {
        return dao.deleteCustomer(customer)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }
}