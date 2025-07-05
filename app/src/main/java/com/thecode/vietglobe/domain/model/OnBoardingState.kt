package com.thecode.vietglobe.domain.model

sealed class OnBoardingState {
    data class Complete(val list: List<OnBoardingPart>) : OnBoardingState()
}
