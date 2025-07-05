package com.thecode.vietglobe.data.repositories

import com.thecode.vietglobe.data.local.AppDatabase
import com.thecode.vietglobe.data.local.model.article.ArticleEntity
import com.thecode.vietglobe.data.remote.datasource.VietGlobeRemoteDataSourceImpl
import com.thecode.vietglobe.data.remote.mapper.ArticleMapper
import com.thecode.vietglobe.domain.model.Article
import com.thecode.vietglobe.domain.model.News
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val networkDataSource: VietGlobeRemoteDataSourceImpl,
    private val articleMapper: ArticleMapper,
    private val appDataBase: AppDatabase
) {

    suspend fun fetchNews(query: String, language: String, sortBy: String): News {
        return networkDataSource.fetchNews(query, language, sortBy)
    }

    suspend fun fetchHeadlinesByCategory(category: String): News {
        return networkDataSource.fetchTopHeadlinesByCategory(category)
    }

    fun getBookmarks(): Flow<List<ArticleEntity>> {
        return appDataBase.getArticlesDao().getAllArticles()
    }

    suspend fun saveBookmark(article: Article) {
        appDataBase.getArticlesDao().insert(articleMapper.mapToEntity(article))
    }

    suspend fun deleteBookmark(url: String) {
        appDataBase.getArticlesDao().deleteByPrimaryId(url)
    }
}
