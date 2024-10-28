package com.example.currencyconverter.Fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyconverter.Adapters.NumberAdapter
import com.example.currencyconverter.Interface.IClickListenerCode
import com.example.currencyconverter.Interface.IClickListenerCode1
import com.example.currencyconverter.Interface.IClickListenerNumber
import com.example.currencyconverter.MainActivity
import com.example.currencyconverter.Models.CurrencyItem
import com.example.currencyconverter.Models.SymbolsName
import com.example.currencyconverter.R
import com.example.currencyconverter.ViewModels.CurrencyViewModel
import com.example.currencyconverter.databinding.FragmentHomeBinding
import kotlin.reflect.full.memberProperties

class HomeFragment : Fragment(), IClickListenerCode, IClickListenerNumber, IClickListenerCode1 {


    private lateinit var binding: FragmentHomeBinding
    private val listCurrency = mutableListOf<CurrencyItem>()
    private lateinit var symbolsName: SymbolsName
    private lateinit var currencyBottomSheetFragment: CurrencyBottomSheetFragment
    private lateinit var currencyBottomSheetFragment1: CurrencyBottomSheetFragment1
    private lateinit var numberAdapter: NumberAdapter
    private val listNumber = mutableListOf<String>()
    private val symbolsMap = mutableMapOf<String, String>()
    private var listCurrencyTemp = mutableListOf<CurrencyItem>()


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

        if (Build.VERSION.SDK_INT >= 33) {
            (activity as MainActivity).onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ) {

                exitOnBackPressed()
            }
        } else {
            (activity as MainActivity).onBackPressedDispatcher.addCallback(
                this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        Log.i("TAG", "handleOnBackPressed: Exit")
                        exitOnBackPressed()
                    }
                })
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        loadDataNumber()
        loadRecyclerView()




        currencyViewModel.getSymbolsApi()
        observerSymbols()


        currencyViewModel.convertCurrencyApi()
        observerConvertCurrency()






        binding.layoutBase.setOnClickListener {
            clickOpenBottomSheetFragment()
        }

        binding.layoutBase1.setOnClickListener {
            clickOpenBottomSheetFragment1()
        }


        ///Change currency
        binding.btnChange.setOnClickListener {
            changeBase()
        }

    }


    private fun loadDataSymbols() {
        val listCode = resources.getStringArray(R.array.currency_codes)

        for (name in listCode) {

            listCurrency.add(CurrencyItem(name = name))
        }
        binding.tvCode.text = listCurrency[0].name
        binding.tvBase.text = symbolsMap[listCurrency[0].name] ?: "Empty"

        binding.tvCode1.text = listCurrency[1].name
        binding.tvBase1.text = symbolsMap[listCurrency[1].name] ?: "Empty"
    }

    private fun clickOpenBottomSheetFragment() {
        listCurrencyTemp = mutableListOf()
        listCurrencyTemp = listCurrency
        currencyBottomSheetFragment = CurrencyBottomSheetFragment(listCurrencyTemp, this)
        currencyBottomSheetFragment.show(parentFragmentManager, currencyBottomSheetFragment.tag)

    }

    private fun clickOpenBottomSheetFragment1() {
        listCurrencyTemp = mutableListOf()
        listCurrencyTemp = listCurrency
        currencyBottomSheetFragment1 = CurrencyBottomSheetFragment1(listCurrencyTemp, this)
        currencyBottomSheetFragment1.show(parentFragmentManager, currencyBottomSheetFragment1.tag)

    }


    override fun onClickItem(position: Int) {
        val name = listCurrency[position].name;
        binding.tvCode.text = name
        binding.tvBase.text = symbolsMap[name] ?: "Empty"
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
        var number = binding.tvBaseAmount.text.toString()
        if (number == "0.00") {
            number = ""
        }
        number += listNumber[position]
        binding.tvBaseAmount.text = number
        Toast.makeText(requireContext(), "Number :${number}", Toast.LENGTH_SHORT).show()
    }


    private fun observerSymbols() {
        currencyViewModel.observerSymbolCurrency().observe(viewLifecycleOwner) { data ->
            symbolsName = data
            for (prop in SymbolsName::class.memberProperties) {
                symbolsMap[prop.name] = prop.get(symbolsName).toString()
            }
            loadDataSymbols()
        }
    }

    private fun observerConvertCurrency() {
        currencyViewModel.observerConvertCurrency().observe(viewLifecycleOwner) { data ->
            Log.d("MyApp", data.toString())
        }
    }

    private fun exitOnBackPressed() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.apply {
            setTitle("Confirm Exit")
            setMessage("Are you sure you want to exist?")
            setCancelable(false)
            setPositiveButton("Yes") { _, _ ->
                requireActivity().finish()
            }
            setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            create()
            show()
        }

    }

    override fun onClickItem1(position: Int) {
        val name = listCurrency[position].name;
        binding.tvCode1.text = name
        binding.tvBase1.text = symbolsMap[name] ?: "Empty"
        currencyBottomSheetFragment1.dismiss()
    }


    private fun changeBase() {

        val tvCode = binding.tvCode.text.toString()
        val tvBase = binding.tvBase.text.toString()
        val tvCode1 = binding.tvCode1.text.toString()
        val tvBase1 = binding.tvBase1.text.toString()

        ///BASE
        binding.tvCode.text = tvCode1
        binding.tvBase.text = tvBase1

        ///Base1
        binding.tvCode1.text = tvCode
        binding.tvBase1.text = tvBase
    }


}