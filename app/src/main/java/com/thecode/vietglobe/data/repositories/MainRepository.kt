package com.thecode.vietglobe.data.repositories

import com.thecode.vietglobe.data.local.datasource.VietGlobeLocalDataSourceImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val localDataSource: VietGlobeLocalDataSourceImpl
) {
    suspend fun setNightModeEnabled(state: Boolean) {
        localDataSource.setNightModeEnabled(state)
    }

    fun isNightModeEnabled(): Flow<Boolean> {
        return localDataSource.isNightModeEnabled()
    }

    suspend fun clearAppData() {
        localDataSource.clearAppData()
    }
}
