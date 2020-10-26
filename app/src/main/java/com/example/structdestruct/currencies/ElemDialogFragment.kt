package com.example.structdestruct.currencies

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import java.text.SimpleDateFormat
import java.util.*


private const val ARG_TITLE = "title"
private const val ARG_FROM = "from"
private const val ARG_TO = "to"
private const val ARG_MIN = "min"
private const val ARG_MAX = "max"

class ElemDialogFragment : DialogFragment() {
    private var title: String? = null
    private var from: String? = null
    private var to: String? = null
    private var min: String? = null
    private var max: String? = null

    @SuppressLint("SimpleDateFormat")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.i("", "dialog created!")
        arguments?.let {
            title = it.getString(ARG_TITLE)
            from = it.getString(ARG_FROM)
            to = it.getString(ARG_TO)
            min = it.getString(ARG_MIN)
            max = it.getString(ARG_MAX)
        }

        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val sdf = SimpleDateFormat("MM/dd/yyyy")
        val netDate = Date((title!!.toLong()) * 1000)

        builder.setTitle(sdf.format(netDate).toString())
            .setMessage("from: $from\nto: $to\nmin: $min\nmax: $max")
            .setPositiveButton("OK", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, id: Int) {
                    Navigation.createNavigateOnClickListener(R.id.action_elem_dialog_to_host_fragment)
                }
            })

        return builder.create()
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String, from: String, to: String, min: String, max: String) =
            ElemDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_FROM, from)
                    putString(ARG_TO, to)
                    putString(ARG_MIN, min)
                    putString(ARG_MAX, max)
                }
            }
    }
}