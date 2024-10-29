package com.example.newsapplication.adapters

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(private val linearLayoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        var visibleItemCount = linearLayoutManager.childCount
        var totalItemCount = linearLayoutManager.itemCount
        var firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()

        if (isLoading() || isLastPage()) {
            return
        }

        if (firstVisibleItemPosition >= 0 && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
            loadMoreItems()
        }
    }

    abstract fun loadMoreItems()
    abstract fun isLoading(): Boolean;
    abstract fun isLastPage(): Boolean

}