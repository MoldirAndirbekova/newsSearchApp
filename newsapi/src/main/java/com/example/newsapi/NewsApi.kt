package com.example.newsapi

import androidx.annotation.IntRange
import com.example.newsapi.models.ArticleDTO
import com.example.newsapi.models.Languages
import com.example.newsapi.models.ResponseDTO
import com.example.newsapi.models.SortBy
import com.example.newsapi.utils.TimeApiKeyInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
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
    ):Result<ResponseDTO<ArticleDTO>>
}

fun NewsApi(
    baseUrl: String,
    apiKey: String,
    okHttpClient: OkHttpClient? = null,
    json: Json.Default = Json,
): NewsApi {
    val retrofit = retrofit(baseUrl, apiKey, okHttpClient, json)
    return retrofit.create()
}

private fun retrofit(
    baseUrl: String,
    apiKey: String,
    okHttpClient: OkHttpClient?,
    json: Json.Default,
): Retrofit {
    val jsonConverter = json.asConverterFactory(MediaType.get("application/json"))

    val modifiedOkHttpClient = (okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
        .addInterceptor(TimeApiKeyInterceptor(apiKey))
        .build()


    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(jsonConverter)
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .client(modifiedOkHttpClient)
        .build()
}


