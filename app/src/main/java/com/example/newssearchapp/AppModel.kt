package com.example.newssearchapp

import android.content.Context
import com.example.common.AndroidLogcatLogger
import com.example.common.AppDispatchers
import com.example.common.Logger
import com.example.news.data.ArticlesRepository
import com.example.news.database.NewsDatabase
import com.example.newsapi.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModel {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient? {
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
            return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        } else {
            return null
        }
    }

    @Provides
    @Singleton
    fun provideNewsApi(okHttpClient: OkHttpClient?): NewsApi {
        val okHttpClient: OkHttpClient? = if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
            OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        } else {
            null
        }

        return NewsApi(
            baseUrl = BuildConfig.NEWS_API_BASE_URL,
            apikey = BuildConfig.NEWS_API_KEY,
            okHttpClient = okHttpClient
        )
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase {
        return NewsDatabase(context)
    }
    @Provides
    @Singleton
    fun provideAppCoroutineDispatchers(): AppDispatchers = AppDispatchers()

    @Provides
    fun provideLogger(): Logger = AndroidLogcatLogger()

}