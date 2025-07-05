package com.thecode.vietglobe.data.remote.service

interface TranslationService {
    suspend fun translateText(text: String, targetLang: String = "vi"): String
} 