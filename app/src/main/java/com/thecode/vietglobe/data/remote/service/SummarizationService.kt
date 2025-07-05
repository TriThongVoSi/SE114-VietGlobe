package com.thecode.vietglobe.data.remote.service
 
interface SummarizationService {
    suspend fun summarizeText(text: String, maxWords: Int = 150): String
} 