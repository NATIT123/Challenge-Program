package com.example.currencyconverter.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.currencyconverter.MainActivity
import com.example.currencyconverter.viewModels.CurrencyViewModel
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
        ///Get value by using navArgs() from NewsFragment
        val article = args.article
        ///Load progressbar
        binding.isLoading = true
        binding.webView.apply {
            //Load WebView
            webViewClient = WebViewClient()
            loadUrl(article.url!!)
        }
        ///Unloading progressbar
        binding.isLoading = false


    }


}