package com.thecode.vietglobe.domain.usecases

import com.thecode.vietglobe.data.repositories.OnBoardingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsOnboardingCompleted @Inject constructor(
    private val repository: OnBoardingRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return repository.isOnboardingCompleted()
    }
}
