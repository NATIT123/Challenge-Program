package com.example.currencyconverter.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.currencyconverter.MainActivity
import com.example.currencyconverter.R
import com.example.currencyconverter.databinding.FragmentChartBinding
import com.example.currencyconverter.models.DataSample
import com.example.currencyconverter.models.SaveCurrency
import com.example.currencyconverter.viewModels.CurrencyViewModel
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry


class ChartFragment : Fragment(), AdapterView.OnItemSelectedListener {


    private lateinit var binding: FragmentChartBinding
    private var codeSelected = ""
    private lateinit var barDataSet: BarDataSet
    private lateinit var currencyViewModel: CurrencyViewModel
    val listColor = listOf(Color.RED, Color.GREEN, Color.BLUE)
    private val ratesMap = mutableMapOf<String, Double>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainActivity = (activity as MainActivity)
        currencyViewModel = mainActivity.viewModel
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        ///Create a spinner to choose currency with data form resources arrays.xml
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.currency_codes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.codeSpinner.adapter = adapter
        }

        ///Handle event when user select item in spinner
        binding.codeSpinner.onItemSelectedListener = this


        ///Save data to database
        val data = DataSample()
        saveCurrency(data.data)
        observerCurrencySaved()


        ///Draw chart
        drawChart()

    }


    ///Create a list data for chart
    private fun loadData(): List<BarEntry> {
        val listCode = resources.getStringArray(R.array.currency_codes)
        val dataValue = mutableListOf<BarEntry>()
        for ((i, name) in listCode.withIndex()) {
            dataValue.add(
                BarEntry(i.toFloat(),
                    ratesMap[codeSelected]?.let {
                        ratesMap[name]?.let { it1 ->
                            listOf(
                                it.toFloat(),
                                it1.toFloat()
                            ).toFloatArray()
                        }
                    })
            )

            if (i == 10) {
                break
            }
        }
        return dataValue
    }

    ///Save data to database
    private fun saveCurrency(listCurrency: List<SaveCurrency>) {
        listCurrency.let {
            currencyViewModel.addCurrency(listCurrency)
        }
    }

    ///Get data from database
    private fun observerCurrencySaved() {
        currencyViewModel.observerCurrencySaved().observe(viewLifecycleOwner) { data ->
            loadDataOffline(data)
        }
    }

    ///Load data from database
    private fun loadDataOffline(listCurrency: List<SaveCurrency>) {
        for (data in listCurrency) {
            ratesMap[data.code.toString()] = data.rate.toString().toDouble()
        }
    }

    ///Handle event when user select item in spinner
    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
        if (parent != null) {
            codeSelected = parent.getItemAtPosition(i).toString()
            drawChart()
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }


    private fun drawChart() {
        barDataSet = BarDataSet(loadData(), "Bar set")
        barDataSet.colors = listColor
        binding.chart.data = BarData(barDataSet)
    }


}