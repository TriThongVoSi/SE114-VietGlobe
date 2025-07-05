package com.thecode.vietglobe.data.remote.service

import com.thecode.vietglobe.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiSummarizationService @Inject constructor() : SummarizationService {
    
    private val model by lazy {
        GenerativeModel(
            modelName = "gemini-2.0-flash",
            apiKey = BuildConfig.GEMINI_API_KEY
        )
    }

    override suspend fun summarizeText(text: String, maxWords: Int): String {
        val prompt = """
            Summarize the following news article in at most $maxWords words.Then translate the summary into Vietnamese. Focus on the main points and key information:
            
            $text
            
            Summary:
        """.trimIndent()

        return try {
            val response = model.generateContent(prompt)
            response.text ?: "Unable to generate summary"
        } catch (e: Exception) {
            "Error generating summary: ${e.message}"
        }
    }
} 