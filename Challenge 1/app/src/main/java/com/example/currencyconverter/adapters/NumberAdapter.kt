package com.example.currencyconverter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.interfaces.IClickListenerNumber
import com.example.currencyconverter.R
import com.example.currencyconverter.databinding.NumberItemBinding

class NumberAdapter(
    private val context: Context,
    private val listNumberItem: MutableList<String>,
    private val mOnClickItemListener: IClickListenerNumber
) :
    RecyclerView.Adapter<NumberAdapter.NumberViewHolder>() {


    inner class NumberViewHolder(val numberItemBinding: NumberItemBinding) :
        RecyclerView.ViewHolder(numberItemBinding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NumberViewHolder {
        val view = NumberItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NumberViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listNumberItem.size
    }

    override fun onBindViewHolder(holder: NumberAdapter.NumberViewHolder, position: Int) {
        val numberItem = listNumberItem[position]
        holder.numberItemBinding.apply {
            tvNumber.text = numberItem
            if (numberItem == "C" || numberItem == "AC") {
                tvNumber.background =
                    ContextCompat.getDrawable(context, R.drawable.backgound_number_delete)
            }
            holder.itemView.setOnClickListener {
                mOnClickItemListener.onClickItemNumber(position)
            }
        }
    }

}