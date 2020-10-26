package com.example.structdestruct.currencies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_host.*
import java.util.ArrayList

class HostFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.layoutManager = LinearLayoutManager(this.context)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = ItemAdapter(ArrayList<ItemList>())

        val mainActivity = (this.activity as MainActivity)
        currency_input.setText(mainActivity.fromCurrency)
        mainActivity.UpdateRecyclerView(mainActivity.fromCurrency, mainActivity.toCurrency, mainActivity.daysLimit)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HostFragment().apply{}
    }
}