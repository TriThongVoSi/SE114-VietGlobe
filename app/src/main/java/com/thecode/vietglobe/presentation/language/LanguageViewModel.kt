package com.thecode.vietglobe.presentation.language

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thecode.vietglobe.domain.usecases.GetLanguages
import com.thecode.vietglobe.domain.usecases.GetLanguagesApiCodes
import com.thecode.vietglobe.domain.usecases.SaveLanguagePreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val getLanguagesApiCodes: GetLanguagesApiCodes,
    private val getLanguages: GetLanguages,
    private val saveLanguagePreference: SaveLanguagePreference
) : ViewModel() {

    val languages = MutableLiveData<List<String>>()

    fun getCurrentLanguages() {
        val list = getLanguages().toMutableList()
        languages.value = list
    }

    fun saveLanguagePref(position: Int) {
        viewModelScope.launch {
            val languageCode = getLanguagesApiCodes()[position]
            saveLanguagePreference.invoke(languageCode)
        }

    }

}