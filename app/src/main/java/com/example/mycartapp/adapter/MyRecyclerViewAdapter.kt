package com.example.mycartapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mycartapp.R
import com.example.mycartapp.database.Customer
import com.example.mycartapp.databinding.ListItemBinding


class MyRecyclerViewAdapter(private val clickListener: (Customer) -> Unit) :
    RecyclerView.Adapter<MyViewHolder>() {
    private val customersList = ArrayList<Customer>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.list_item, parent, false
        )
        return MyViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return customersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(customersList[position], clickListener)
    }

    fun setList(customer: List<Customer>) {
        customersList.clear()
        customersList.addAll(customer)
    }
}

class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(customer: Customer, clickListener: (Customer) -> Unit) {
        binding.nameTextView.text = customer.name
        binding.emailTextView.text = customer.email
        binding.listItemLayout.setOnClickListener() {
            clickListener(customer)
        }


    }

}