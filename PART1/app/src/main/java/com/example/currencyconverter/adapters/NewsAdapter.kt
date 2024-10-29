package com.example.currencyconverter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.currencyconverter.databinding.ItemArticlePreviewBinding
import com.example.currencyconverter.models.Article
class NewsAdapter(private val mOnClickItem: onClickItem) :
    RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(val itemArticlePreviewBinding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(itemArticlePreviewBinding.root)


    interface onClickItem {
        fun onClick(position: Int)
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view =
            ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val news = differ.currentList[position]
        holder.itemArticlePreviewBinding.apply {
            Glide.with(holder.itemView).load(news.urlToImage).into(ivArticleImage)
            tvTitle.text = news.title
            tvSource.text = news.source?.name
            tvPublishedAt.text = news.publishedAt
            tvDescription.text = news.description
            root.setOnClickListener {
                mOnClickItem.onClick(position)
            }
        }
    }


}