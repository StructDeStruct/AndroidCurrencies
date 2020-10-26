package com.example.structdestruct.currencies

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class ItemAdapter (val list: List<ItemList>) : RecyclerView.Adapter<ItemAdapter.ExampleViewHolder>() {
    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateStock: TextView = itemView.date
        val value: TextView = itemView.value
        val currency: TextView = itemView.currency
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return ExampleViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = list[position]

        val sdf = SimpleDateFormat("MM/dd/yyyy")
        val netDate = Date(currentItem.dateStock * 1000)

        holder.dateStock.text = sdf.format(netDate).toString()
        holder.value.text = currentItem.value.toString()
        holder.currency.text = currentItem.toCurrency.toString()

        holder.itemView.setOnClickListener{
            val args = Bundle()
            args.putString("title", currentItem.dateStock.toString())
            args.putString("from", currentItem.fromCurrency)
            args.putString("to", currentItem.toCurrency)
            args.putString("min", currentItem.minOfDay.toString())
            args.putString("max", currentItem.maxOfDay.toString())

            holder.itemView.findNavController().navigate(
                R.id.action_host_fragment_to_dialog_fragment, args
            )
        }
    }
}