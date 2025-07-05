package com.thecode.vietglobe.data.remote.datasource

import com.thecode.vietglobe.domain.model.News

interface VietGlobeRemoteDataSource {

    suspend fun fetchNews(query: String, language: String, sortBy: String): News

    suspend fun fetchTopHeadlinesByCategory(category: String): News
}