package com.example.newsapi

import androidx.annotation.IntRange
import com.example.newsapi.models.Article
import com.example.newsapi.models.Languages
import com.example.newsapi.models.Response
import com.example.newsapi.models.SortBy
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date

interface NewsApi {
    /**
    *  API details [here](https://newsapi.org/docs/endpoints/everything)
    */
    @GET("/everything")
    suspend fun everything(
        @Query("q") query: String?= null,
        @Query("from") from: Date?= null,
        @Query("to") to: Date?= null,
        @Query("language") languages: List<Languages>?= null,
        @Query("sortBy") sortBy: SortBy?= null,
        @Query("pageSize") @IntRange(from = 0, to = 100) pageSize: Int = 100,
        @Query("page") @IntRange(from = 0, to = 100) page: Int = 1,
    ):Response<Article>
}

fun NewsApi(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
): NewsApi {
    val retrofit = retrofit(baseUrl, okHttpClient)
    return retrofit.create()
}

private fun retrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient?,
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .run { if (okHttpClient != null) client(okHttpClient) else this}
        .build()
}


