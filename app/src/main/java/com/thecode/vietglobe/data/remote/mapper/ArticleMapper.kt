package com.thecode.vietglobe.data.remote.mapper

import com.thecode.vietglobe.data.local.model.article.ArticleEntity
import com.thecode.vietglobe.data.local.model.source.SourceEntity
import com.thecode.vietglobe.domain.model.Article
import com.thecode.vietglobe.domain.model.SourceItem
import javax.inject.Inject

class
ArticleMapper @Inject constructor() :
    EntityMapper<ArticleEntity, Article> {

    override fun mapToEntity(domainModel: Article): ArticleEntity {
        return ArticleEntity(
            sourceItemToEntity(domainModel.source),
            domainModel.author,
            domainModel.title,
            domainModel.description,
            domainModel.url,
            domainModel.urlToImage,
            domainModel.publishedAt,
            domainModel.content
        )
    }

    private fun sourceItemToEntity(source: SourceItem): SourceEntity {
        return SourceEntity(
            source.id.toString(),
            source.name
        )
    }

    override fun mapToDomain(entity: ArticleEntity): Article {
        TODO("Not yet implemented")
    }
}
