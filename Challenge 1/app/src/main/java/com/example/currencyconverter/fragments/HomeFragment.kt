package com.example.currencyconverter.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyconverter.MainActivity
import com.example.currencyconverter.R
import com.example.currencyconverter.adapters.NumberAdapter
import com.example.currencyconverter.databinding.FragmentHomeBinding
import com.example.currencyconverter.interfaces.IClickListenerCode
import com.example.currencyconverter.interfaces.IClickListenerCode1
import com.example.currencyconverter.interfaces.IClickListenerNumber
import com.example.currencyconverter.models.CurrencyItem
import com.example.currencyconverter.models.DataSample
import com.example.currencyconverter.models.RatesAToL
import com.example.currencyconverter.models.RatesMToZ
import com.example.currencyconverter.models.SaveCurrency
import com.example.currencyconverter.models.SymbolsName
import com.example.currencyconverter.viewModels.CurrencyViewModel
import java.text.DecimalFormat
import kotlin.reflect.full.memberProperties


class HomeFragment : Fragment(), IClickListenerCode, IClickListenerNumber, IClickListenerCode1 {


    private lateinit var binding: FragmentHomeBinding
    private var listCurrency = mutableListOf<CurrencyItem>()
    private lateinit var symbolsName: SymbolsName
    private lateinit var currencyBottomSheetFragment: CurrencyBottomSheetFragment
    private lateinit var currencyBottomSheetFragment1: CurrencyBottomSheetFragment1
    private lateinit var numberAdapter: NumberAdapter
    private val listNumber = mutableListOf<String>()
    private val symbolsMap = mutableMapOf<String, String>()
    private var listCurrencyTemp = listOf<CurrencyItem>()
    private var listCurrencyTemp1 = listOf<CurrencyItem>()
    private lateinit var ratesAToL: RatesAToL
    private lateinit var ratesMToZ: RatesMToZ
    private val ratesMap = mutableMapOf<String, Double>()


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
        val mainActivity = (activity as MainActivity)
        currencyViewModel = mainActivity.viewModel


        ///Handle back press
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

        val mainActivity = (activity as MainActivity)

        ///Save data to database
        val data = DataSample()
        saveCurrency(data.data)

        ///Check internet connection
        if (mainActivity.isConnected) {
            currencyViewModel.getSymbolsApi()
            observerSymbols()
            observerErrorSymbols()


            currencyViewModel.getLatestRatesAToL()
            observerLatestRatesAToL()


            currencyViewModel.getLatestRatesMToZ()
            observerLatestRatesMToZ()

            observerErrorRates()

            loadDataNumber()
            loadRecyclerView()

            if (binding.isLoadingLayout == true && binding.isLoadingLayout1 == true) {
                binding.isLoadingLayout = false
                binding.isLoadingLayout1 = false
                observerCurrencySaved()
            }


        } else {
            observerCurrencySaved()
            loadDataNumber()
            loadRecyclerView()
            Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT)
                .show()
        }






        //Open bottom sheet code
        binding.layoutBase.setOnClickListener {
            clickOpenBottomSheetFragment()
        }

        //Open bottom sheet code1
        binding.layoutBase1.setOnClickListener {
            clickOpenBottomSheetFragment1()
        }


        ///Change currency
        binding.btnChange.setOnClickListener {
            changeBase()
        }

    }


    ///Load Data into View
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


    ///Open bottom sheet
    private fun clickOpenBottomSheetFragment() {
        listCurrencyTemp = mutableListOf()
        listCurrencyTemp = listCurrency
        currencyBottomSheetFragment =
            CurrencyBottomSheetFragment(
                listCurrencyTemp,
                this
            )
        currencyBottomSheetFragment.show(parentFragmentManager, currencyBottomSheetFragment.tag)

    }

    ///Open bottom sheet
    private fun clickOpenBottomSheetFragment1() {
        listCurrencyTemp1 = mutableListOf()
        listCurrencyTemp1 = listCurrency
        currencyBottomSheetFragment1 =
            CurrencyBottomSheetFragment1(
                listCurrencyTemp1,
                this
            )
        currencyBottomSheetFragment1.show(
            parentFragmentManager,
            currencyBottomSheetFragment1.tag
        )

    }

    //Handle event click bottom
    override fun onClickItem(position: Int) {
        val name = listCurrency[position].name;
        binding.tvCode.text = name
        binding.tvBase.text = symbolsMap[name] ?: "Empty"
        currencyBottomSheetFragment.dismiss()
        covertRates()
    }


    ///Load data into recycler view
    private fun loadDataNumber() {
        listNumber.clear()
        listNumber.addAll(listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", ",", "0", "AC"))
    }

    ///Load recycler view
    private fun loadRecyclerView() {
        binding.isLoadingSymbol = true
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
        binding.isLoadingSymbol = false
    }

    ///Handle event click number
    @SuppressLint("SetTextI18n")
    override fun onClickItemNumber(position: Int) {
        var number = binding.tvBaseAmount.text.toString()
        if (number == "0.00") {
            number = ""
        }
        var length = number.length - number.count { it == '.' }
        length -= number.count { it == ',' }
        val symbol = listNumber[position]
        if (symbol == "C" || symbol == "AC") {
            number = ""
            binding.tvBaseAmount.text = "0.00"
            binding.tvBaseAmount1.text = "0.00"
            listNumber[listNumber.size - 1] = "AC"
            numberAdapter.notifyItemChanged(listNumber.size - 1)
        } else if (length < 9) {
            if (symbol == ",") {
                if (number.isEmpty()) {
                    number = "0,"
                } else if (!number.contains(",")) {
                    number += listNumber[position]
                }
            } else {
                number += listNumber[position]
            }
        }
        if (!number.contains(",") && number.isNotEmpty()) {
            listNumber[listNumber.size - 1] = "C"
            numberAdapter.notifyItemChanged(listNumber.size - 1)
        }
        if (number.isNotEmpty()) {
            var result = number
            if (!number.contains(",")) {
                val formatter = DecimalFormat("#,###")
                val formattedString = formatter.format(number.replace(".", "").toLong())
                result = formattedString
            }
            binding.tvBaseAmount.text = result
            covertRates()
        }

    }

    ///Convert rates
    @SuppressLint("SetTextI18n")
    private fun covertRates() {
        val code = binding.tvCode.text.toString()
        val code1 = binding.tvCode1.text.toString()
        var amount = binding.tvBaseAmount.text.toString()
        //Check amount is empty
        if (amount !== "0.0") {
            amount = amount.replace(".", "").replace(",", ".")
            ///Calculate result
            val result =
                (1 / ratesMap[code]!!) / (1 / ratesMap[code1]!!) * amount.toDouble()
            ///Format result
            val formatter = DecimalFormat("#,###.##")
            var formattedString = formatter.format(result).replace(",", ".")
            if (formattedString == "0") {
                formattedString = "%.6f".format(result).replace(",", ".")
            }
            binding.tvBaseAmount1.text = formattedString
        }
    }


    ///Observer Data Symbols
    private fun observerSymbols() {
        binding.isLoadingSymbol = true
        binding.isLoadingLayout = true
        binding.isLoadingLayout1 = true
        currencyViewModel.observerSymbolCurrency().observe(viewLifecycleOwner) { data ->
            symbolsName = data
            for (prop in SymbolsName::class.memberProperties) {
                symbolsMap[prop.name] = prop.get(symbolsName).toString()
            }
            loadDataSymbols()
        }
    }

    ///Observer Data Rates
    private fun observerLatestRatesAToL() {
        currencyViewModel.observerLatestRatesAToL().observe(viewLifecycleOwner) { data ->
            if (data != null) {
                ratesAToL = data
                for (prop in RatesAToL::class.memberProperties) {
                    ratesMap[prop.name] = prop.get(ratesAToL).toString().toDouble()
                }
            }
        }
    }


    private fun observerLatestRatesMToZ() {
        currencyViewModel.observerLatestRatesMToZ().observe(viewLifecycleOwner) { data ->
            if (data != null) {
                ratesMToZ = data
                for (prop in RatesMToZ::class.memberProperties) {
                    ratesMap[prop.name] = prop.get(ratesMToZ).toString().toDouble()
                }
                binding.isLoadingLayout = false
                binding.isLoadingLayout1 = false
                binding.isLoadingSymbol = false
            }

        }
    }

    ///Observer Error
    private fun observerErrorSymbols() {
        currencyViewModel.observerErrorHandleSymbol().observe(viewLifecycleOwner) { data ->
            if (data != null) {
                binding.isLoadingLayout = true
                binding.isLoadingLayout1 = true
                Toast.makeText(requireContext(), data.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    ///Observer Error
    private fun observerErrorRates() {
        currencyViewModel.observerErrorHandleRates().observe(viewLifecycleOwner) { data ->
            if (data != null) {
                binding.isLoadingLayout = true
                binding.isLoadingLayout1 = true
                Toast.makeText(requireContext(), data.message, Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

    ///Exit app
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

        covertRates()

    }

    ///Get data from database
    private fun observerCurrencySaved() {
        currencyViewModel.observerCurrencySaved().observe(viewLifecycleOwner) { data ->
            loadDataOffline(data)
            loadDataSymbols()
        }
    }


    private fun saveCurrency(listCurrency: List<SaveCurrency>) {
        listCurrency.let {
            currencyViewModel.addCurrency(listCurrency)
        }
    }

    ///Load data from database
    private fun loadDataOffline(listCurrency: List<SaveCurrency>) {
        for (data in listCurrency) {
            symbolsMap[data.code.toString()] = data.name.toString()
            ratesMap[data.code.toString()] = data.rate.toString().toDouble()
        }
    }


}

