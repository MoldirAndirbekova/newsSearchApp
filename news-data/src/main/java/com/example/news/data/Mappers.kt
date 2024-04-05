package com.example.news.data

import com.example.news.data.model.Article
import com.example.news.data.model.Source
import com.example.news.database.models.ArticleDBO
import com.example.news.database.models.SourceDBO
import com.example.newsapi.models.ArticleDTO
import com.example.newsapi.models.SourceDTO

internal fun SourceDBO.toSource() : Source {
    return Source(id = id, name = name)
}

internal fun SourceDTO.toSource() : Source {
    return Source(id = id ?: name, name = name)
}

internal fun SourceDTO.toSourceDbo() : SourceDBO {
    return SourceDBO(id = id ?: name, name = name)
}

internal fun ArticleDBO.toArticle() : Article {
    return Article(
        id = id,
        source = source.toSource(),
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
    )
}

internal fun ArticleDTO.toArticle(): Article{
    return Article(
        source = source.toSource(),
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
    )
}


internal fun ArticleDTO.toArticleDbo(): ArticleDBO{
    return ArticleDBO(
        source = source.toSourceDbo(),
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
    )
}