package com.thecode.vietglobe.presentation.main.bookmark

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.thecode.vietglobe.R
import com.thecode.vietglobe.base.BaseFragment
import com.thecode.vietglobe.databinding.FragmentBookmarksBinding
import com.thecode.vietglobe.domain.model.Article
import com.thecode.vietglobe.domain.model.DataState
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter

@AndroidEntryPoint
class BookmarksFragment : BaseFragment() {

    private val viewModel: BookmarkViewModel by viewModels()

    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: BookmarkRecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)

        subscribeObserver()
        initViews()
        initRecyclerView()
        viewModel.getBookmarks()

        return binding.root
    }

    private fun initRecyclerView() {
        recyclerView = binding.recyclerViewBookmark
        recyclerAdapter = BookmarkRecyclerViewAdapter(
            onDeleteBookmark = {
                viewModel.deleteBookmark(it.url)
            },
            onOpenNews = {
                openNews(it)
            },
            onShareNews = {
                shareNews(it)
            },
            onOpenNewsInBrowser = {
                openNewsInBrowser(it)
            },
            onSummarizeNews = {
                showSummaryBottomSheet(it)
            },
            onTranslateNews = {
                showTranslationBottomSheet(it)
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = SlideInBottomAnimationAdapter(recyclerAdapter)
    }

    private fun initViews() {
        binding.apply {
            refreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorPrimaryDark
            )
            val typedValue = TypedValue()
            val theme: Resources.Theme = requireContext().theme
            theme.resolveAttribute(R.attr.primaryCardBackgroundColor, typedValue, true)
            @ColorInt val color = typedValue.data
            refreshLayout.setProgressBackgroundColorSchemeColor(color)
            refreshLayout.setOnRefreshListener {
                viewModel.getBookmarks()
            }
        }
    }

    private fun hideEmptyStateLayout() {
        binding.layoutBookmarkEmpty.isVisible = false
    }

    private fun showEmptyStateLayout() {
        binding.layoutBookmarkEmpty.isVisible = true
    }

    private fun hideLoadingProgress() {
        binding.refreshLayout.isRefreshing = false
    }

    private fun showLoadingProgress() {
        binding.refreshLayout.isRefreshing = true
    }

    private fun subscribeObserver() {
        viewModel.articles.observe(
            viewLifecycleOwner,
            {
                when (it) {
                    is DataState.Success -> {
                        populateRecyclerView(it.data)
                    }

                    is DataState.Loading -> {
                        showLoadingProgress()
                    }

                    is DataState.Error -> {
                        hideLoadingProgress()
                        showEmptyStateLayout()
                    }
                }
            }
        )
    }

    private fun populateRecyclerView(articles: List<Article>) {
        if (articles.isEmpty()) {
            showEmptyStateLayout()
            recyclerView.adapter = null
            recyclerAdapter.notifyDataSetChanged()
        } else {
            recyclerAdapter.setArticleListItems(articles)
            hideEmptyStateLayout()
            hideLoadingProgress()
            recyclerView.scheduleLayoutAnimation()
        }
    }

    private fun openNews(article: Article) {
        Log.d("BookmarkClick", "Article url: ${article.url}")
        if (article.url.isNullOrEmpty()) {
            showErrorDialog(getString(R.string.error), "Bài viết này không có đường dẫn hợp lệ.")
            return
        }
        loadWebviewDialog(article)
    }

    private fun openNewsInBrowser(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun shareNews(article: Article) {
        openSharingIntent(article)
    }

    private fun showSummaryBottomSheet(article: Article) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_summary, null)
        bottomSheetDialog.setContentView(bottomSheetView)

        val summaryLoading = bottomSheetView.findViewById<View>(R.id.summary_loading)
        val textSummary = bottomSheetView.findViewById<android.widget.TextView>(R.id.text_summary)

        viewModel.summaryState.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    summaryLoading.isVisible = true
                    textSummary.isVisible = false
                }
                is DataState.Success -> {
                    summaryLoading.isVisible = false
                    textSummary.isVisible = true
                    textSummary.text = dataState.data
                }
                is DataState.Error -> {
                    summaryLoading.isVisible = false
                    textSummary.isVisible = true
                    textSummary.text = getString(R.string.service_unavailable)
                }
            }
        }

        val fullContent = "${article.description}\n\n${article.content}"
        viewModel.getSummary(fullContent)

        bottomSheetDialog.show()
    }

    private fun showTranslationBottomSheet(article: Article) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_translation, null)
        bottomSheetDialog.setContentView(bottomSheetView)

        val translationLoading = bottomSheetView.findViewById<View>(R.id.translation_loading)
        val textTranslation = bottomSheetView.findViewById<android.widget.TextView>(R.id.text_translation)

        viewModel.translationState.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    translationLoading.isVisible = true
                    textTranslation.isVisible = false
                }
                is DataState.Success -> {
                    translationLoading.isVisible = false
                    textTranslation.isVisible = true
                    textTranslation.text = dataState.data
                }
                is DataState.Error -> {
                    translationLoading.isVisible = false
                    textTranslation.isVisible = true
                    textTranslation.text = getString(R.string.service_unavailable)
                }
            }
        }

        val fullContent = "${article.description}\n\n${article.content}"
        viewModel.getTranslation(fullContent)

        bottomSheetDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
