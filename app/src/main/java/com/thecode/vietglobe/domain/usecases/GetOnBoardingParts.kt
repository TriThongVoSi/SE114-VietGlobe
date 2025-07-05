package com.thecode.vietglobe.domain.usecases

import com.thecode.vietglobe.data.repositories.OnBoardingRepository
import com.thecode.vietglobe.domain.model.OnBoardingPart
import javax.inject.Inject

class GetOnBoardingParts @Inject constructor(
    private val repository: OnBoardingRepository
) {
    operator fun invoke(): List<OnBoardingPart> {
        return repository.getOnBoardingList()
    }
}
