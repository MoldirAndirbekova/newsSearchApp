package com.example.news.data

import com.example.news.data.model.Article
import com.example.news.database.NewsDatabase
import com.example.newsapi.NewsApi
import kotlinx.coroutines.flow.Flow

class ArticlesRepository(
    private val database: NewsDatabase,
    private val api:NewsApi,
) {
    suspend fun getAll(): Flow<Article>{
        api.everything()
        TODO("Not Implemented")
    }
}