package com.example.currencyconverter.Fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.Adapters.CurrencyAdapter
import com.example.currencyconverter.Interface.IClickListener
import com.example.currencyconverter.Models.CurrencyItem
import com.example.currencyconverter.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CurrencyBottomSheetFragment(
    private val mListItems: MutableList<CurrencyItem>,
    val iClickListener: IClickListener
) : BottomSheetDialogFragment(),IClickListener {

    private lateinit var currencyAdapter: CurrencyAdapter
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog =super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = LayoutInflater.from(context).inflate(R.layout.layout_bottom_sheet,null)
        bottomSheetDialog.setContentView(view)

        val rcvData=view.findViewById<RecyclerView>(R.id.rcv_data)
        val linearLayoutManager = LinearLayoutManager(context)
        rcvData.layoutManager = linearLayoutManager


        currencyAdapter = CurrencyAdapter(mListItems,iClickListener)
        rcvData.adapter = currencyAdapter

        rcvData.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL ))

        return bottomSheetDialog
    }

    override fun onClickItem(position: Int) {
        iClickListener.onClickItem(position)
    }

    override fun deleteItem(position: Int) {
        TODO("Not yet implemented")
    }

}