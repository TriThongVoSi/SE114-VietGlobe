package com.thecode.vietglobe.presentation.main.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thecode.vietglobe.domain.model.Article
import com.thecode.vietglobe.domain.model.DataState
import com.thecode.vietglobe.domain.usecases.DeleteBookmark
import com.thecode.vietglobe.domain.usecases.GetBookmarks
import com.thecode.vietglobe.data.remote.service.SummarizationService
import com.thecode.vietglobe.data.remote.service.TranslationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getBookmarks: GetBookmarks,
    private val deleteBookmark: DeleteBookmark,
    private val summarizationService: SummarizationService,
    private val translationService: TranslationService
) : ViewModel() {
    private val _articles = MutableLiveData<DataState<List<Article>>>()
    val articles: LiveData<DataState<List<Article>>>
        get() = _articles

    private val _summaryState = MutableLiveData<DataState<String>>()
    val summaryState: LiveData<DataState<String>>
        get() = _summaryState

    private val _translationState = MutableLiveData<DataState<String>>()
    val translationState: LiveData<DataState<String>> get() = _translationState

    fun getBookmarks() {
        viewModelScope.launch {
            _articles.value.let { _ ->
                getBookmarks.getBookmarks().onEach {
                    _articles.value = it
                }.launchIn(viewModelScope)
            }
        }
    }

    fun deleteBookmark(url: String) {
        viewModelScope.launch {
            deleteBookmark.invoke(url)
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
