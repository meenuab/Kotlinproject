package com.example.mycartapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycartapp.R
import com.example.mycartapp.adapter.MyRecyclerViewAdapter
import com.example.mycartapp.database.Customer
import com.example.mycartapp.database.CustomerDatabase
import com.example.mycartapp.databinding.ActivityMainBinding
import com.example.mycartapp.repository.CustomerRepository
import com.example.mycartapp.viewmodel.CustomerViewModel
import com.example.mycartapp.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var customerViewModel: CustomerViewModel
    private lateinit var adapter: MyRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        val dao = CustomerDatabase.getInstance(application).customerDao
        val repository = CustomerRepository(dao)
        val factory = ViewModelFactory(repository)
        customerViewModel = ViewModelProvider(this, factory).get(CustomerViewModel::class.java)
        binding.myViewModel = customerViewModel
        binding.lifecycleOwner = this

        initRecyclerView()

        customerViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initRecyclerView() {
        binding.customerRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter =
            MyRecyclerViewAdapter({ selectedItem: Customer ->
                listItemClicked(selectedItem)
            })
        binding.customerRecyclerView.adapter = adapter
        displayCustomers()
    }

    private fun displayCustomers() {
        customerViewModel.customers.observe(this, Observer {
            Log.i("My Tag", it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(customer: Customer) {
        customerViewModel.initUpdateAndDelete(customer)
    }
}
