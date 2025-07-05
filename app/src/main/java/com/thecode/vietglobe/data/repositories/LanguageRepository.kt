package com.thecode.vietglobe.data.repositories

import com.thecode.vietglobe.data.local.datasource.VietGlobeLocalDataSourceImpl
import com.thecode.vietglobe.domain.model.LanguageName
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LanguageRepository @Inject constructor(
    private val localDataSource: VietGlobeLocalDataSourceImpl
) {
    fun getLanguages(): List<LanguageName> {
        return LanguageName.entries
    }

    fun getUserLanguagePreference(): Flow<String> {
        return localDataSource.getUserLanguagePreference()
    }

    suspend fun setUserLanguagePreference(lang: String) {
        localDataSource.setUserLanguagePreference(lang)
    }
}