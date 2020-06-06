package com.example.mycartapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer_details")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "customer_id")
    var id: Int,
    @ColumnInfo(name = "customer_name")
    var name: String,
    @ColumnInfo(name = "customer_email")
    var email: String
)

