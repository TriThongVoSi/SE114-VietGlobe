package com.thecode.vietglobe.domain.usecases

import com.thecode.vietglobe.data.repositories.NewsRepository
import com.thecode.vietglobe.domain.model.Article
import javax.inject.Inject

class SaveBookmark @Inject constructor(
    private val repository: NewsRepository
) {
    suspend fun invoke(article: Article) {
        repository.saveBookmark(article)
    }
}
