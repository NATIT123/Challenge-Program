package com.example.currencyconverter.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.adapters.CurrencyAdapter
import com.example.currencyconverter.interfaces.IClickListenerCode
import com.example.currencyconverter.models.CurrencyItem
import com.example.currencyconverter.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CurrencyBottomSheetFragment(
    private val mListItems: MutableList<CurrencyItem>,
    private val iClickListenerCode: IClickListenerCode
) : BottomSheetDialogFragment(), IClickListenerCode {

    private lateinit var currencyAdapter: CurrencyAdapter

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = LayoutInflater.from(context).inflate(R.layout.layout_bottom_sheet, null)
        bottomSheetDialog.setContentView(view)


        val rcvData = view.findViewById<RecyclerView>(R.id.rcv_data)
        val linearLayoutManager = LinearLayoutManager(context)
        rcvData.layoutManager = linearLayoutManager


        currencyAdapter = CurrencyAdapter(mListItems, iClickListenerCode)
        rcvData.adapter = currencyAdapter

        rcvData.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        return bottomSheetDialog
    }

    override fun onClickItem(position: Int) {
        iClickListenerCode.onClickItem(position)
    }



}