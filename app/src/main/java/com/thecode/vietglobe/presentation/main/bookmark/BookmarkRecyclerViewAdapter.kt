package com.thecode.vietglobe.presentation.main.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.thecode.vietglobe.R
import com.thecode.vietglobe.databinding.AdapterNewsLandscapeBinding
import com.thecode.vietglobe.domain.model.Article


class BookmarkRecyclerViewAdapter(
    private val onDeleteBookmark: (Article) -> Unit,
    private val onOpenNews: (Article) -> Unit,
    private val onOpenNewsInBrowser: (String) -> Unit,
    private val onShareNews: (Article) -> Unit,
    private val onSummarizeNews: (Article) -> Unit,
    private val onTranslateNews: (Article) -> Unit
) :
    RecyclerView.Adapter<BookmarkRecyclerViewAdapter.NewsViewHolder>() {

    private lateinit var binding: AdapterNewsLandscapeBinding
    private var newsList: ArrayList<Article> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        binding =
            AdapterNewsLandscapeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]
        holder.tvNewsTitle.text = news.title
        holder.tvNewsDate.text = news.publishedAt?.split("T")?.get(0) ?: ""
        holder.tvPublisherName.text = news.source.name

        holder.btnShare.setOnClickListener {
            onShareNews(news)
        }

        holder.btnDelete.setOnClickListener {
            onDeleteBookmark(news)
            newsList.removeAt(position)
            notifyItemRemoved(position)
        }

        holder.btnSummarize.setOnClickListener {
            onSummarizeNews(news)
        }

        holder.btnTranslate.setOnClickListener {
            onTranslateNews(news)
        }

        holder.itemView.setOnClickListener {
            onOpenNews(news)
        }

        Glide.with(holder.itemView.context).load(news.urlToImage)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .apply(RequestOptions().centerCrop())
            .into(holder.image)
    }

    fun setArticleListItems(newsList: List<Article>) {
        this.newsList = ArrayList(newsList)
        notifyDataSetChanged()
    }

    class NewsViewHolder(binding: AdapterNewsLandscapeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvNewsTitle = binding.textTitle
        val tvPublisherName = binding.textNamePublisher
        val image = binding.imageNews
        val btnShare = binding.btnShare
        val btnSummarize = binding.btnSummarize
        val btnDelete = binding.btnDelete
        val tvNewsDate = binding.textChipDate
        val btnTranslate = binding.btnTranslate
    }
}
