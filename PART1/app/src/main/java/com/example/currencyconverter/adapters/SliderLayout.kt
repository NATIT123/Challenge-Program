package com.example.currencyconverter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.models.SliderInfo
import com.example.currencyconverter.databinding.SliderLayoutBinding

class SliderLayout(private val listSliderInfo: MutableList<SliderInfo>) :
    RecyclerView.Adapter<SliderLayout.SliderViewHolder>() {

    inner class SliderViewHolder(val sliderLayoutBinding: SliderLayoutBinding) :
        RecyclerView.ViewHolder(sliderLayoutBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val view = SliderLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listSliderInfo.size
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val sliderInfo = listSliderInfo[position]
        holder.sliderLayoutBinding.apply {
            imageSlider.setImageAsset(sliderInfo.image)
            headerTitle.setText(sliderInfo.header)
            subtitleTitle.setText(sliderInfo.subtitle)
        }
    }
}