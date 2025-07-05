package com.thecode.vietglobe.domain.usecases

import com.thecode.vietglobe.data.repositories.MainRepository
import javax.inject.Inject

class ResetAppPreferences @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke() {
        repository.clearAppData()
    }
}
