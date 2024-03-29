package com.example.news.main

data class ArticleUI(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val url: String,
)