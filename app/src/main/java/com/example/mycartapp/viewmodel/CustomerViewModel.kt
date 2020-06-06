package com.example.mycartapp.viewmodel

import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.example.mycartapp.database.Customer
import com.example.mycartapp.repository.CustomerRepository
import kotlinx.coroutines.launch

class CustomerViewModel(private val repository: CustomerRepository) : ViewModel(), Observable {
    val customers = repository.customer
    private var isUpdateOrDelete = false
    private lateinit var customerToUpdateOrDelete: Customer

    @Bindable
    val name = MutableLiveData<String>()

    @Bindable
    val email = MutableLiveData<String>()

    @Bindable
    val saveOrUpadete = MutableLiveData<String>()

    @Bindable
    val clearOrDelete = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpadete.value = "SAVE"
        clearOrDelete.value = "CLEAR"
    }


    fun SaveOrUpdate() {
        if (name.value == null) {
            statusMessage.value =
                Event("Please enter subscriber's name")
        } else if (email.value == null) {
            statusMessage.value =
                Event("Please enter subscriber's email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.value!!).matches()) {
            statusMessage.value =
                Event("Please enter a correct email address")
        } else {
            if (isUpdateOrDelete) {
                customerToUpdateOrDelete.name = name.value!!
                customerToUpdateOrDelete.email = email.value!!
                update(customerToUpdateOrDelete)
            } else {

                val inputName: String = name.value!!
                val inputEmail: String = email.value!!
                insert(Customer(0, inputName, inputEmail))
                name.value = null
                email.value = null
            }
        }
    }

    fun ClearOrDelete() {
        if (isUpdateOrDelete) {
            delete(customerToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    fun insert(customer: Customer) {
        viewModelScope.launch {
            val newRowId = repository.insert(customer)
            if (newRowId > -1) {
                statusMessage.value =
                    Event("Subscriber Inserted Successfully")
            } else {
                statusMessage.value =
                    Event("Error Occurred")
            }
        }
    }

    fun update(customer: Customer) {
        viewModelScope.launch {
            val rows = repository.update(customer)
            if (rows > 0) {
                name.value = null
                email.value = null
                isUpdateOrDelete = false
                customerToUpdateOrDelete = customer
                saveOrUpadete.value = "SAVE"
                clearOrDelete.value = "CLEAR"
                statusMessage.value =
                    Event("Customer Updated Successfully")
            } else {
                statusMessage.value =
                    Event("Error Occurred")
            }
        }
    }

    fun delete(customer: Customer) {
        viewModelScope.launch {
            val rows = repository.delete(customer)
            if (rows > 0) {
                name.value = null
                email.value = null
                isUpdateOrDelete = false
                customerToUpdateOrDelete = customer
                saveOrUpadete.value = "SAVE"
                clearOrDelete.value = "CLEAR"
                statusMessage.value =
                    Event("Customer Deleted Successfully")
            } else {
                statusMessage.value =
                    Event("Error Occurred")
            }
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            val rows = repository.deleteAll()
            if (rows > 0) {
                statusMessage.value =
                    Event("Successfully Deleted All Customers")
            } else {
                statusMessage.value =
                    Event("Error Occurred")
            }

        }
    }

    fun initUpdateAndDelete(customer: Customer) {
        name.value = customer.name
        email.value = customer.email
        isUpdateOrDelete = true
        customerToUpdateOrDelete = customer
        saveOrUpadete.value = "UPDATE"
        clearOrDelete.value = "DELETE"

    }


    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}
