package com.example.currencyconverter.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.Interface.IClickListenerCode1
import com.example.currencyconverter.Models.CurrencyItem
import com.example.currencyconverter.databinding.CurrencyItemBinding

class CurrencyAdapter1(
    private val listCurrencyItem: MutableList<CurrencyItem>,
    private val mOnClickItemListener: IClickListenerCode1
) :
    RecyclerView.Adapter<CurrencyAdapter1.CurrencyViewHolder>() {


    inner class CurrencyViewHolder(val currencyItemBinding: CurrencyItemBinding) :
        RecyclerView.ViewHolder(currencyItemBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = CurrencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listCurrencyItem.size
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currencyItem = listCurrencyItem[position]
        holder.currencyItemBinding.apply {
            tvItem.text = currencyItem.name
            holder.itemView.setOnClickListener {
                mOnClickItemListener.onClickItem1(position)
            }
        }
    }


}