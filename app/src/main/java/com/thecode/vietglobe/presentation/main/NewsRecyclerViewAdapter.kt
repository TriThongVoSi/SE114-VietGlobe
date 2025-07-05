package com.thecode.vietglobe.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.thecode.vietglobe.R
import com.thecode.vietglobe.databinding.AdapterNewsBinding
import com.thecode.vietglobe.domain.model.Article


class NewsRecyclerViewAdapter(
    private val onSaveBookmark: (Article) -> Unit,
    private val onOpenNews: (Article) -> Unit,
    private val onOpenNewsInBrowser: (String) -> Unit,
    private val onShareNews: (Article) -> Unit,
    private val onSummarizeNews: (Article) -> Unit,
    private val onTranslateNews: (Article) -> Unit
) : RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder>() {

    private lateinit var binding: AdapterNewsBinding
    private var newsList: List<Article> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        binding = AdapterNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]
        holder.tvNewsTitle.text = news.title
        holder.tvNewsDescription.text = news.description
        holder.tvNewsDate.text = news.publishedAt?.split("T")?.get(0) ?: ""
        holder.tvPublisherName.text = news.source.name

        Glide.with(holder.itemView.context).load(news.urlToImage)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .apply(RequestOptions().centerCrop())
            .into(holder.image)

        holder.btnShare.setOnClickListener {
            onShareNews(news)
        }

        holder.btnBookmark.setOnClickListener {
            onSaveBookmark(news)
        }

        holder.btnSummarize.setOnClickListener {
            onSummarizeNews(news)
        }
        holder.btnTranslate.setOnClickListener {
            onTranslateNews(news)
        }

        // Mở chi tiết khi click vào card hoặc ảnh hoặc tiêu đề
        holder.itemView.setOnClickListener {
            onOpenNews(news)
        }
    }

    fun setArticleListItems(newsList: List<Article>) {
        this.newsList = emptyList()
        this.newsList = newsList
        notifyDataSetChanged()
    }

    class NewsViewHolder(binding: AdapterNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvNewsTitle: TextView = binding.textTitle
        val tvNewsDescription: TextView = binding.textDescription
        val tvPublisherName: TextView = binding.textNamePublisher
        val image: ImageView = binding.imageNews
        val btnBookmark: ImageView = binding.btnBookmark
        val btnShare: ImageView = binding.btnShare
        val btnSummarize: ImageView = binding.btnSummarize
        val btnTranslate: ImageView = binding.btnTranslate
        val tvNewsDate: TextView = binding.textChipDate
    }
}
