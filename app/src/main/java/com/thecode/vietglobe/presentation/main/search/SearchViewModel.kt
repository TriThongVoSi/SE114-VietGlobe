package com.thecode.vietglobe.presentation.main.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thecode.vietglobe.domain.model.Article
import com.thecode.vietglobe.domain.model.DataState
import com.thecode.vietglobe.domain.model.News
import com.thecode.vietglobe.domain.usecases.GetSearchNews
import com.thecode.vietglobe.domain.usecases.SaveBookmark
import com.thecode.vietglobe.data.remote.service.SummarizationService
import com.thecode.vietglobe.data.remote.service.TranslationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchNews: GetSearchNews,
    private val saveBookmark: SaveBookmark,
    private val summarizationService: SummarizationService,
    private val translationService: TranslationService
) : ViewModel() {
    private val _searchState = MutableLiveData<DataState<News>>()
    val searchState: LiveData<DataState<News>>
        get() = _searchState

    private val _summaryState = MutableLiveData<DataState<String>>()
    val summaryState: LiveData<DataState<String>>
        get() = _summaryState

    private val _translationState = MutableLiveData<DataState<String>>()
    val translationState: LiveData<DataState<String>> get() = _translationState

    fun getSearchNews(query: String, language: String, category: String) {
        Log.d("Search", "$query - $language - $category")
        viewModelScope.launch {
            _searchState.value.let { _ ->
                getSearchNews.getSearchNews(query, language, category).onEach {
                    _searchState.value = it
                }.launchIn(viewModelScope)
            }
        }
    }

    fun saveBookmark(article: Article) {
        viewModelScope.launch {
            saveBookmark.invoke(article)
        }
    }

    fun getSummary(content: String) {
        viewModelScope.launch {
            _summaryState.value = DataState.Loading
            try {
                val summary = summarizationService.summarizeText(content)
                _summaryState.value = DataState.Success(summary)
            } catch (e: Exception) {
                _summaryState.value = DataState.Error(e)
            }
        }
    }

    fun getTranslation(content: String) {
        viewModelScope.launch {
            _translationState.value = DataState.Loading
            try {
                val translation = translationService.translateText(content)
                _translationState.value = DataState.Success(translation)
            } catch (e: Exception) {
                _translationState.value = DataState.Error(e)
            }
        }
    }
}
