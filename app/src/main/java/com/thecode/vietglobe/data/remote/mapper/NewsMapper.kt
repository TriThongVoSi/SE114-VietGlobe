package com.thecode.vietglobe.data.remote.mapper

import com.thecode.vietglobe.data.local.model.article.ArticleEntity
import com.thecode.vietglobe.data.remote.model.NewsObjectResponse
import com.thecode.vietglobe.domain.model.Article
import com.thecode.vietglobe.domain.model.News
import com.thecode.vietglobe.domain.model.SourceItem
import javax.inject.Inject

class NewsMapper @Inject constructor() :
    EntityMapper<NewsObjectResponse, News> {
    override fun mapToDomain(entity: NewsObjectResponse): News {
        return News(
            entity.status.toString(),
            entity.totalResults,
            entity.articles.map {
                mapFromNewsItems(it)
            }
        )
    }

    private fun mapFromNewsItems(article: NewsObjectResponse.Result): Article {
        return Article(
            SourceItem(
                article.source.id,
                article.source.name.toString()
            ),
            article.author,
            article.title,
            article.description,
            article.url,
            article.urlToImage,
            article.publishedAt,
            article.content,
        )
    }

    fun newsEntityToItem(article: ArticleEntity): Article {
        return Article(
            SourceItem(article.source.id, article.source.name.toString()),
            article.author,
            article.title,
            article.description,
            article.url,
            article.urlToImage,
            article.publishedAt,
            article.content
        )
    }

    override fun mapToEntity(domainModel: News): NewsObjectResponse {
        throw Exception("Not used")
    }
}
