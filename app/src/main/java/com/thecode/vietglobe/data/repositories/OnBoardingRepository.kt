package com.thecode.vietglobe.data.repositories

import com.thecode.vietglobe.R
import com.thecode.vietglobe.data.local.datasource.VietGlobeLocalDataSourceImpl
import com.thecode.vietglobe.domain.model.OnBoardingPart
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OnBoardingRepository @Inject constructor(
    private val localDataSource: VietGlobeLocalDataSourceImpl
) {

    suspend fun setOnboardingCompleted() {
        localDataSource.setOnboardingCompleted()
    }

    fun isOnboardingCompleted(): Flow<Boolean> {
        return localDataSource.isOnboardingCompleted()
    }

    fun getOnBoardingList(): List<OnBoardingPart> {
        return listOf(
            OnBoardingPart(
                image = R.raw.lottie_earth,
                title = R.string.title_onboarding_1,
                description = R.string.description_onboarding_1
            ),
            OnBoardingPart(
                image = R.raw.lottie_social_media,
                title = R.string.title_onboarding_2,
                description = R.string.description_onboarding_2
            ),
            OnBoardingPart(
                image = R.raw.lottie_digital,
                title = R.string.title_onboarding_3,
                description = R.string.description_onboarding_3
            )
        )
    }
}
