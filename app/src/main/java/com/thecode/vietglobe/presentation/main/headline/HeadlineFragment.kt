package com.thecode.vietglobe.presentation.main.headline

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.thecode.vietglobe.R
import com.thecode.vietglobe.R.color.colorPrimary
import com.thecode.vietglobe.R.color.colorPrimaryDark
import com.thecode.vietglobe.base.BaseFragment
import com.thecode.vietglobe.databinding.FragmentHeadlineBinding
import com.thecode.vietglobe.domain.model.Article
import com.thecode.vietglobe.domain.model.DataState
import com.thecode.vietglobe.presentation.main.NewsRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter


@AndroidEntryPoint
class HeadlineFragment : BaseFragment() {

    private val viewModel: HeadlineViewModel by viewModels()

    private var _binding: FragmentHeadlineBinding? = null
    private val binding get() = _binding!!

    private var category: String = "General"
    private lateinit var recyclerAdapter: NewsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeadlineBinding.inflate(inflater, container, false)

        subscribeObservers()
        initViews()
        initRecyclerView()
        fetchNews()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchNews() {
        viewModel.getHeadlines(
            category.lowercase()
        )
        binding.recyclerViewNews.scheduleLayoutAnimation()
    }

    private fun showInternetConnectionErrorLayout() {
        if (recyclerAdapter.itemCount > 0) {
            showErrorDialog(
                getString(R.string.network_error),
                getString(R.string.check_internet)
            )
        } else {
            binding.included.apply {
                layoutBadState.isVisible = true
                textState.text = getString(R.string.internet_connection_error)
                btnRetry.isVisible = true
            }
        }
    }

    private fun showBadStateLayout() {
        if (recyclerAdapter.itemCount > 0) {
            showErrorDialog(
                getString(R.string.error),
                getString(R.string.service_unavailable)
            )
        } else {
            binding.included.apply {
                layoutBadState.isVisible = true
                textState.text = getString(R.string.no_result_found)
                btnRetry.isVisible = true
            }
        }
    }

    private fun hideBadStateLayout() {
        binding.included.layoutBadState.isVisible = false
    }

    private fun subscribeObservers() {
        viewModel.headlineState.observe(
            viewLifecycleOwner
        ) {
            when (it) {
                is DataState.Success -> {
                    hideBadStateLayout()
                    hideLoadingProgress()
                    populateRecyclerView(it.data.articles)
                }

                is DataState.Loading -> {
                    showLoadingProgress()
                }

                is DataState.Error -> {
                    hideLoadingProgress()
                    showInternetConnectionErrorLayout()
                }
            }
        }
    }

    private fun hideLoadingProgress() {
        binding.refreshLayout.isRefreshing = false
    }

    private fun showLoadingProgress() {
        binding.refreshLayout.isRefreshing = true
    }

    private fun initRecyclerView() {
        recyclerAdapter = NewsRecyclerViewAdapter(
            onSaveBookmark = {
                saveBookmark(it)
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
        binding.recyclerViewNews.layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewNews.adapter = SlideInBottomAnimationAdapter(recyclerAdapter)
    }

    private fun initViews() {
        binding.apply {
            included.btnRetry.setOnClickListener { fetchNews() }
            refreshLayout.setColorSchemeResources(
                colorPrimary,
                colorPrimary,
                colorPrimaryDark,
                colorPrimaryDark
            )
            val typedValue = TypedValue()
            val theme: Resources.Theme = requireContext().theme
            theme.resolveAttribute(R.attr.primaryCardBackgroundColor, typedValue, true)
            @ColorInt val color = typedValue.data
            refreshLayout.setProgressBackgroundColorSchemeColor(color)
            refreshLayout.setOnRefreshListener {
                fetchNews()
            }

            categoryChipGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.chip_all -> {
                        category = getString(R.string.general_category)
                    }
                    R.id.chip_science -> {
                        category = getString(R.string.science_category)
                    }
                    R.id.chip_entertainment -> {
                        category = getString(R.string.entertainment_category)
                    }
                    R.id.chip_technology -> {
                        category = getString(R.string.technology_category)
                    }
                    R.id.chip_sports -> {
                        category = getString(R.string.sports_category)
                    }
                }
                fetchNews()
            }
        }
    }

    private fun populateRecyclerView(articles: List<Article>) {
        if (articles.isEmpty()) {
            showBadStateLayout()
        } else {
            recyclerAdapter.setArticleListItems(articles)
            binding.recyclerViewNews.scheduleLayoutAnimation()
        }
    }

    fun saveBookmark(article: Article) {
        viewModel.saveBookmark(article)
        showSuccessDialog(
            getString(R.string.success),
            getString(R.string.bookmark_saved)
        )
    }

    private fun openNews(article: Article) {
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
        val textSummary = bottomSheetView.findViewById<TextView>(R.id.text_summary)

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
        val textTranslation = bottomSheetView.findViewById<TextView>(R.id.text_translation)

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
}

