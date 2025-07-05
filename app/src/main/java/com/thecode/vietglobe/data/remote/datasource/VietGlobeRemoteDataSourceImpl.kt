package com.thecode.vietglobe.data.remote.datasource

import com.thecode.vietglobe.data.remote.NewsApiRemoteService
import com.thecode.vietglobe.data.remote.mapper.NewsMapper
import com.thecode.vietglobe.domain.model.News
import javax.inject.Inject

class VietGlobeRemoteDataSourceImpl @Inject constructor(
    private val apiService: NewsApiRemoteService,
    private val newsMapper: NewsMapper

) : VietGlobeRemoteDataSource {
    override suspend fun fetchNews(query: String, language: String, sortBy: String): News {
        return newsMapper.mapToDomain(apiService.getAllNews(query, language, sortBy))
    }

    override suspend fun fetchTopHeadlinesByCategory(
        category: String
    ): News {
        return newsMapper.mapToDomain(
            apiService.getTopHeadlinesByCategory(
                category
            )
        )
    }
}
