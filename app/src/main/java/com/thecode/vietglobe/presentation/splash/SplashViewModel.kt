package com.thecode.vietglobe.presentation.splash

import androidx.lifecycle.ViewModel
import com.thecode.vietglobe.domain.usecases.IsOnboardingCompleted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isOnboardingCompletedUseCase: IsOnboardingCompleted
) : ViewModel() {

    val isOnboardingCompleted: Flow<Boolean> = isOnboardingCompletedUseCase()

}
