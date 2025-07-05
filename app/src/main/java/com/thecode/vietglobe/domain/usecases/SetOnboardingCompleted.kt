package com.thecode.vietglobe.domain.usecases

import com.thecode.vietglobe.data.repositories.OnBoardingRepository
import javax.inject.Inject

class SetOnboardingCompleted @Inject constructor(
    private val repository: OnBoardingRepository
) {
    suspend operator fun invoke() {
        repository.setOnboardingCompleted()
    }
}
