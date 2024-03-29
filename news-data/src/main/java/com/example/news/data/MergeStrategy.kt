package com.example.news.data

interface MergeStrategy<E> {
    fun merge(right: E, left: E): E
}

class RequestResponseMergeStrategy<T: Any>: MergeStrategy<RequestResult<T>> {
    override fun merge(right: RequestResult<T>, left: RequestResult<T>): RequestResult<T> {
        return when {
            right is RequestResult.InProgress && left is RequestResult.InProgress ->
                merge(right, left)

            right is RequestResult.Success && left is RequestResult.InProgress ->
                merge(right,left)

            right is RequestResult.InProgress && left is RequestResult.Success ->
                merge(right,left)
            right is RequestResult.Success && left is RequestResult.Error ->
                merge(right,left)

            else -> error("Unimplemented branch")
        }
    }

    private fun merge(
        cache: RequestResult.InProgress<T>,
        server: RequestResult.InProgress<T>
    ): RequestResult<T> {
        return when {
            server.data != null -> RequestResult.InProgress(server.data)
            else -> RequestResult.InProgress(cache.data)
        }
    }

    private fun merge(
        cache: RequestResult.Success<T>,
        server: RequestResult.InProgress<T>
    ): RequestResult<T> {
        return RequestResult.InProgress(cache.data)
    }


    private fun merge(
        cache: RequestResult.InProgress<T>,
        server: RequestResult.Success<T>
    ): RequestResult<T> {
        return RequestResult.InProgress(server.data)
    }

    private fun merge(
        cache: RequestResult.Success<T>,
        server: RequestResult.Error<T>
    ): RequestResult<T> {
        return RequestResult.Error(data = cache.data, error = server.error)
    }
}