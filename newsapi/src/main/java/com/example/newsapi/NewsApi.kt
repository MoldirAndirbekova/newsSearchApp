package com.example.newsapi

import androidx.annotation.IntRange
import com.example.newsapi.models.Languages
import com.example.newsapi.models.SortBy
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date

interface NewsApi {

    @GET("/everything")
    fun everything(
        @Query("q") query: String?= null,
        @Query("from") from: Date?= null,
        @Query("to") to: Date?= null,
        @Query("language") languages: List<Languages>?= null,
        @Query("sortBy") sortBy: SortBy?= null,
        @Query("pageSize") @IntRange(from = 0, to = 100) pageSize: Int = 100,
        @Query("page") @IntRange(from = 0, to = 100) page: Int = 1,
    )
}
