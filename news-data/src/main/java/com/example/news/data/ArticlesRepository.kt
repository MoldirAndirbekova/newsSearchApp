package com.example.news.data

import com.example.news.data.model.Article
import com.example.news.database.NewsDatabase
import com.example.news.database.models.ArticleDBO
import com.example.newsapi.NewsApi
import com.example.newsapi.models.ArticleDTO
import com.example.newsapi.models.ResponseDTO
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import com.example.common.Logger
import kotlinx.coroutines.flow.catch

class ArticlesRepository @Inject constructor(
    private val database: NewsDatabase,
    private val api:NewsApi,
    private val logger: Logger,

) {
    fun getAll(
        query: String,
        mergeStrategy: MergeStrategy<RequestResult<List<Article>>> = RequestResponseMergeStrategy()): Flow<RequestResult<List<Article>>>{
        val cashedAllArticles: Flow<RequestResult<List<Article>>> = getAllFromDatabase()

        val remoteArticles: Flow<RequestResult<List<Article>>> = getAllFromServer(query)
        return cashedAllArticles.combine(remoteArticles, mergeStrategy::merge)
            .flatMapLatest { result ->
                if (result is RequestResult.Success) {
                    database.articleDao.observeAll()
                        .map {
                            dbos -> dbos.map { it.toArticle() }}
                        .map { RequestResult.Success(it) }
                }
                else {
                    flowOf(result)
                }
            }

    }
//    internal fun <T: Any> RequestResult<T?>.requireData(): T = checkNotNull(data)

    private fun getAllFromDatabase(): Flow<RequestResult<List<Article>>> {
        val dbRequest = database.articleDao::getAll.asFlow()
            .map { RequestResult.Success(it) }
            .catch {
                RequestResult.Error<List<ArticleDBO>> (error = it)
                logger.e(LOG_TAG, "Error getting from db. Cause = $it")
            }
        val start = flowOf<RequestResult<List<ArticleDBO>>>(RequestResult.InProgress())
        return merge(start, dbRequest)
            .map { result ->
                result.map{ articlesDbos ->
                    articlesDbos.map { it.toArticle() }}
            }
    }
    private companion object {
        const val LOG_TAG = "ArticleRepository"
    }

    private fun getAllFromServer(query: String): Flow<RequestResult<List<Article>>> {
        val apiRequest = flow { emit(api.everything(query = query)) }
            .onEach { result ->
                if (result.isSuccess) {
                    saveArticlesToCache(result.getOrThrow().articles)
                }
            }
            .onEach { result ->
                if(result.isFailure) {
                    logger.e(LOG_TAG, "Error getting data from server. Cause = ${result.exceptionOrNull()}")
                }
            }
            .map {it.toRequestResult() }
        val start = flowOf<RequestResult<ResponseDTO<ArticleDTO>>>(RequestResult.InProgress())
        return merge(apiRequest, start)
            .map { result: RequestResult<ResponseDTO<ArticleDTO>> ->
                result.map { response ->
                    response.articles.map {it.toArticle()}
                }
            }
    }

    private suspend fun saveArticlesToCache(data: List<ArticleDTO>) {
        val dbos = data.map { articleDto -> articleDto.toArticleDbo()  }
        database.articleDao.insert(dbos)
    }

    private suspend fun saveNetResponseToCache(data: List<ArticleDTO>) {
        val dbos = data.map { articleDTO -> articleDTO.toArticleDbo() }
        database.articleDao.insert(dbos)
    }


//    private fun getCashedArticles() = database.articleDao
//        .getAll()
//        .map { RequestResult.Success(it) }
}

