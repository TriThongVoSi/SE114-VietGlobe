package com.thecode.vietglobe.presentation.newsdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thecode.vietglobe.data.remote.service.SummarizationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor(
    private val summarizationService: SummarizationService
) : ViewModel() {

    private val _summary = MutableLiveData<String>()
    val summary: LiveData<String> = _summary

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun summarizeArticle(content: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val summary = summarizationService.summarizeText(content)
                _summary.value = summary
            } catch (e: Exception) {
                _summary.value = "Failed to generate summary: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
