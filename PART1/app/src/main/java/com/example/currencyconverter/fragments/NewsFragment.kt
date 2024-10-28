package com.example.currencyconverter.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyconverter.adapters.NewsAdapter
import com.example.currencyconverter.MainActivity
import com.example.currencyconverter.R
import com.example.currencyconverter.viewModels.CurrencyViewModel
import com.example.currencyconverter.databinding.FragmentNewsBinding
import com.example.newsapplication.Models.Article
import com.example.newsapplication.adapters.PaginationScrollListener


class NewsFragment : Fragment(), NewsAdapter.onClickItem {
    private lateinit var binding: FragmentNewsBinding
    private var listArticles = mutableListOf<Article>()
    private lateinit var mNewsAdapter: NewsAdapter
    private lateinit var currencyViewModel: CurrencyViewModel

    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var currentPage = 1;
    private var totalPage = 7;


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(activity)

        binding.rcvFragmentBreaking.apply {
            layoutManager = linearLayoutManager
            adapter = mNewsAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        currencyViewModel.getBreakingNewsApi(currentPage)
        observerNews()

        binding.rcvFragmentBreaking.addOnScrollListener(object :
            PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true

                binding.progressBar.visibility = View.VISIBLE

                currentPage += 1
                loadNextPage()

            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

        })

    }

    private fun loadNextPage() {
        Handler(Looper.getMainLooper()).postDelayed({
            currencyViewModel.getBreakingNewsApi(currentPage)
            isLoading = false
            binding.progressBar.visibility = View.GONE
            if (currentPage == totalPage) {
                isLastPage = true
            }
        }, 2000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currencyViewModel = (activity as MainActivity).viewModel
        mNewsAdapter = NewsAdapter(this@NewsFragment)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewsBinding.inflate(layoutInflater)

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observerNews() {
        currencyViewModel.observerBreakingNews().observe(viewLifecycleOwner) { it ->
            this.listArticles.addAll(it)
            mNewsAdapter.differ.submitList(listArticles)
            mNewsAdapter.notifyDataSetChanged()
        }
    }


    override fun onClick(position: Int) {
        val news = mNewsAdapter.differ.currentList[position]
        val bundle = Bundle().apply {
            putSerializable("article", news)
        }
        findNavController().navigate(R.id.action_newsFragment_to_articleFragment, bundle)
    }
}