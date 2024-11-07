package com.github.ebrahimi16153.mvifoodapp.util

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.github.ebrahimi16153.mvifoodapp.R


fun Spinner.setUpSpinner(list: MutableList<out Any>, callback: (String) -> Unit) {

    val adapter = ArrayAdapter(this.context, R.layout.item_spinner, list)
    adapter.setDropDownViewResource(R.layout.item_spinner_list)
    this.adapter = adapter

    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
          callback(list[position].toString())
        }
        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }


}
