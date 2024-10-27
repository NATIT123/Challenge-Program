//package com.example.currencyconverter.Fragments
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.webkit.WebViewClient
//import android.widget.Toast
//import androidx.navigation.NavArgs
//import androidx.navigation.fragment.navArgs
//import com.example.currencyconverter.MainActivity
//import com.example.currencyconverter.databinding.FragmentArticleBinding
//
//
//class ArticleFragment : Fragment() {
//
//    private lateinit var binding: FragmentArticleBinding
//    private val args: ArticleFragmentArgs by navArgs()
//    private lateinit var newsViewModel: NewsViewModel
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        binding = FragmentArticleBinding.inflate(layoutInflater)
//
//        // Inflate the layout for this fragment
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        newsViewModel = (activity as MainActivity).viewModel
//        val article = args.article
//        binding.webView.apply {
//            webViewClient = WebViewClient()
//            loadUrl(article.url!!)
//        }
//
//
//
//        binding.fab.setOnClickListener {
//            onClickSaveArticle(article)
//        }
//    }
//
//    private fun onClickSaveArticle(article: Article) {
//        article.let {
//            newsViewModel.addNews(article)
//            Toast.makeText(requireContext(), "News Saved", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//}