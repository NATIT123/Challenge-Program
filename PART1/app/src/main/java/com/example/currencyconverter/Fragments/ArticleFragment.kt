package com.example.currencyconverter.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.currencyconverter.MainActivity
import com.example.currencyconverter.ViewModels.CurrencyViewModel
import com.example.currencyconverter.databinding.FragmentArticleBinding


class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding
    private val args: ArticleFragmentArgs by navArgs()
    private lateinit var currencyViewModel: CurrencyViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentArticleBinding.inflate(layoutInflater)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currencyViewModel = (activity as MainActivity).viewModel
        val article = args.article
        Log.d("MyApp", article.toString())
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url!!)
        }


    }


}