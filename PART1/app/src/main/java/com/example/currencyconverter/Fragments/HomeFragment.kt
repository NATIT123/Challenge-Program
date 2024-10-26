package com.example.currencyconverter.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyconverter.Interface.IClickListener
import com.example.currencyconverter.Models.CurrencyItem
import com.example.currencyconverter.R
import com.example.currencyconverter.databinding.FragmentHomeBinding


class HomeFragment : Fragment(),IClickListener {


    private lateinit var binding: FragmentHomeBinding
    private val listCurrency = mutableListOf<CurrencyItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.openBottom.setOnClickListener {
            clickOpenBottomSheetFragment()
        }
    }

    private fun clickOpenBottomSheetFragment() {
        listCurrency.clear()
        listCurrency.add(CurrencyItem("name1","image1"))
        listCurrency.add(CurrencyItem("name22","image1"))
        listCurrency.add(CurrencyItem("name3","image1"))
        listCurrency.add(CurrencyItem("name4","image1"))
        val currencyBottomSheetFragment= CurrencyBottomSheetFragment(listCurrency,this)

        currencyBottomSheetFragment.show(parentFragmentManager,currencyBottomSheetFragment.tag)
    }

    override fun onClickItem(position: Int) {
        val name = listCurrency[position].name;
        Toast.makeText(requireContext(), "Name :${name}", Toast.LENGTH_SHORT).show()
    }

    override fun deleteItem(position: Int) {
        TODO("Not yet implemented")
    }


}