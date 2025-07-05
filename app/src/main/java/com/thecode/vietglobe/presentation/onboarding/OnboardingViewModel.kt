package com.thecode.vietglobe.presentation.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thecode.vietglobe.domain.model.OnBoardingState
import com.thecode.vietglobe.domain.usecases.GetOnBoardingParts
import com.thecode.vietglobe.domain.usecases.SetOnboardingCompleted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val setOnboardingCompleted: SetOnboardingCompleted,
    private val getOnBoardingParts: GetOnBoardingParts
) : ViewModel() {

    private val _state = MutableLiveData<OnBoardingState>()
    val state: LiveData<OnBoardingState>
        get() = _state

    fun getOnBoardingSlide() {
        viewModelScope.launch {
            val list = getOnBoardingParts()
            _state.value = OnBoardingState.Complete(list)
        }
    }

    fun setOnboardingCompleted() {
        viewModelScope.launch {
            setOnboardingCompleted.invoke()
        }
    }
}
