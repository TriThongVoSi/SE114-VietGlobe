package com.thecode.vietglobe.data.local.datasource

import com.thecode.vietglobe.data.local.VietGlobeDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VietGlobeLocalDataSourceImpl @Inject constructor(
    private val dataStore: VietGlobeDataStore
) : VietGlobeLocalDataSource {
    override fun isOnboardingCompleted(): Flow<Boolean> {
        return dataStore.isOnboardingCompleted()
    }

    override suspend fun setOnboardingCompleted() {
        dataStore.setOnboardingCompleted()
    }

    override fun isNightModeEnabled(): Flow<Boolean> {
        return dataStore.isNightModeEnabled()
    }

    override suspend fun setNightModeEnabled(state: Boolean) {
        dataStore.setNightModeEnabled(state)
    }

    override fun getUserLanguagePreference(): Flow<String> {
        return dataStore.getUserLanguagePreference()
    }

    override suspend fun setUserLanguagePreference(lang: String) {
        dataStore.setUserLanguagePreference(lang)
    }

    override suspend fun clearAppData() {
        dataStore.clearSession()
    }
}