package com.thecode.vietglobe.domain.usecases

import com.thecode.vietglobe.data.repositories.MainRepository
import javax.inject.Inject

class SetNightModeEnabled @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(state: Boolean) {
        repository.setNightModeEnabled(state)
    }
}
