package com.thecode.vietglobe.data.remote.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class DeepSeekTranslationService : TranslationService {
    private val apiKey = "YOUR_API_KEY"
    private val apiUrl = "https://openrouter.ai/api/v1/chat/completions"

    override suspend fun translateText(text: String, targetLang: String): String = withContext(Dispatchers.IO) {
        val prompt = "Translate the following text to Vietnamese:\n\n$text"
        val requestBody = JSONObject().apply {
            put("model", "deepseek/deepseek-chat-v3-0324:free")
            put("messages", JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", prompt)
                })
            })
        }
        val client = OkHttpClient()
        val body = requestBody.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(apiUrl)
            .addHeader("Authorization", "Bearer $apiKey")
            .post(body)
            .build()
        return@withContext try {
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                throw Exception("API call failed with code ${response.code}")
            }
            val responseBody = response.body?.string() ?: throw Exception("Empty response body")
            val json = JSONObject(responseBody)
            json.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content")
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
} 