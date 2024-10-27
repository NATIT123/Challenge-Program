package com.example.currencyconverter.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyconverter.Adapters.NumberAdapter
import com.example.currencyconverter.Interface.IClickListener
import com.example.currencyconverter.Interface.IClickListenerNumber
import com.example.currencyconverter.MainActivity
import com.example.currencyconverter.Models.CurrencyItem
import com.example.currencyconverter.Models.SymbolsName
import com.example.currencyconverter.R
import com.example.currencyconverter.ViewModels.CurrencyViewModel
import com.example.currencyconverter.databinding.FragmentHomeBinding
import com.example.newsapplication.Models.Article
import kotlin.reflect.KProperty1


class HomeFragment : Fragment(), IClickListener, IClickListenerNumber {


    private lateinit var binding: FragmentHomeBinding
    private val listCurrency = mutableListOf<CurrencyItem>()
    private lateinit var symbolsName: SymbolsName
    private lateinit var currencyBottomSheetFragment: CurrencyBottomSheetFragment
    private lateinit var numberAdapter: NumberAdapter
    private val listNumber = mutableListOf<String>()


    private lateinit var currencyViewModel: CurrencyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currencyViewModel = (activity as MainActivity).viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        loadDataNumber()
        loadRecyclerView()




        currencyViewModel.getSymbolsApi()
        observerSymbols()


        binding.layoutBase.setOnClickListener {
            clickOpenBottomSheetFragment()
        }

    }

    private fun clickOpenBottomSheetFragment() {
        listCurrency.clear()
        val listCode = resources.getStringArray(R.array.currency_codes)
        val v = mapOf<String, String>(

        )

        for (name in listCode) {
            listCurrency.add(CurrencyItem(name = name))
        }
        currencyBottomSheetFragment = CurrencyBottomSheetFragment(listCurrency, this)


        currencyBottomSheetFragment.show(parentFragmentManager, currencyBottomSheetFragment.tag)
    }

    override fun onClickItem(position: Int) {
        val name = listCurrency[position].name;
        Toast.makeText(requireContext(), "Name :${name}", Toast.LENGTH_SHORT).show()
        currencyBottomSheetFragment.dismiss()
    }


    private fun loadDataNumber() {
        listNumber.clear()
        listNumber.addAll(listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "0", "C"))
    }

    private fun loadRecyclerView() {
        numberAdapter = NumberAdapter(requireContext(), listNumber, this)
        binding.rcvNumber.apply {
            adapter = numberAdapter
            layoutManager = GridLayoutManager(
                requireContext(),
                3,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    override fun onClickItemNumber(position: Int) {
        val name = listNumber[position];
        Toast.makeText(requireContext(), "Number :${name}", Toast.LENGTH_SHORT).show()
    }


    private fun observerSymbols() {
        currencyViewModel.observerSymbolCurrency().observe(viewLifecycleOwner) { data ->
            symbolsName = data
        }
    }

    private fun observerFlags() {
        currencyViewModel.observerFlag().observe(viewLifecycleOwner) { data ->
            Log.d("MyApp", data.toString())
        }
    }

}