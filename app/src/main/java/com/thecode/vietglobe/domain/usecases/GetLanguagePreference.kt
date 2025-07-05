package com.thecode.vietglobe.domain.usecases

import com.thecode.vietglobe.data.repositories.LanguageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLanguagePreference @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    operator fun invoke(): Flow<String> {
        return languageRepository.getUserLanguagePreference()
    }
}
