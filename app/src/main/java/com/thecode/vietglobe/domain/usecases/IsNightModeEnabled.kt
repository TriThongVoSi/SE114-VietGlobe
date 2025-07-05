package com.thecode.vietglobe.domain.usecases

import com.thecode.vietglobe.data.repositories.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsNightModeEnabled @Inject constructor(
    private val repository: MainRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return repository.isNightModeEnabled()
    }
}
