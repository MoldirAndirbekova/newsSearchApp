package com.example.news.main

import com.example.news.data.ArticlesRepository
import com.example.news.data.RequestResult
import com.example.news.data.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.example.news.data.model.Article as DataArticle

class GetAllArticlesUseCase @Inject constructor(private val repository: ArticlesRepository) {

    operator fun invoke(): Flow<RequestResult<List<Article>>> {
        return repository.getAll()
            .map { requestResult ->
                requestResult.map { articles -> articles.map { it.toUIArticle() } }
            }
    }
}
private fun DataArticle.toUIArticle(): Article {
    TODO("Not yet implemented")
}