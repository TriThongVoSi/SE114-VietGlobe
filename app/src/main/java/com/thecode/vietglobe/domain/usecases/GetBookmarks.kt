package com.thecode.vietglobe.domain.usecases

import com.thecode.vietglobe.data.remote.mapper.NewsMapper
import com.thecode.vietglobe.data.repositories.NewsRepository
import com.thecode.vietglobe.domain.model.Article
import com.thecode.vietglobe.domain.model.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetBookmarks @Inject constructor(
    private val repository: NewsRepository,
    private val newsMapper: NewsMapper

) {
    suspend fun getBookmarks(): Flow<DataState<List<Article>>> = flow {
        emit(DataState.Loading)
        repository.getBookmarks().collect { it ->
            if (it.isEmpty()) {
                emit(DataState.Error(Exception("Data must not be empty")))
            } else {
                emit(DataState.Success(it.map { newsMapper.newsEntityToItem(it) }))
            }
        }
    }.flowOn(Dispatchers.IO)
}
