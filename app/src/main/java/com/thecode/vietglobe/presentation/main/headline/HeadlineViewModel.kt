package com.thecode.vietglobe.presentation.main.headline

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thecode.vietglobe.domain.model.Article
import com.thecode.vietglobe.domain.model.DataState
import com.thecode.vietglobe.domain.model.News
import com.thecode.vietglobe.domain.usecases.GetHeadlines
import com.thecode.vietglobe.domain.usecases.SaveBookmark
import com.thecode.vietglobe.data.remote.service.SummarizationService
import com.thecode.vietglobe.data.remote.service.TranslationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeadlineViewModel @Inject constructor(
    private val getHeadlines: GetHeadlines,
    private val saveBookmark: SaveBookmark,
    private val summarizationService: SummarizationService,
    private val translationService: TranslationService
) : ViewModel() {
    private val _headlineState = MutableLiveData<DataState<News>>()
    val headlineState: LiveData<DataState<News>>
        get() = _headlineState

    private val _summaryState = MutableLiveData<DataState<String>>()
    val summaryState: LiveData<DataState<String>>
        get() = _summaryState

    private val _translationState = MutableLiveData<DataState<String>>()
    val translationState: LiveData<DataState<String>> get() = _translationState

    fun getHeadlines(category: String) {
        Log.d("Headlines", "Category : $category")
        viewModelScope.launch {
            getHeadlines.invoke(category).collect {
                _headlineState.value = it
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
