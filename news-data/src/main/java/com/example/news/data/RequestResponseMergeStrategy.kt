package com.example.news.data.com.example.news.data

import com.example.news.data.RequestResult

interface RequestResponseMergeStrategy<E> {
    fun merge(right: RequestResult<E>, left: RequestResult<E>): RequestResult<E>
}