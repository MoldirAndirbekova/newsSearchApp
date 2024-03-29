package com.example.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineDispatcher


open class AppDispatchers(
    val default: CoroutineDispatcher = Dispatchers.Default,
    val io: CoroutineDispatcher = Dispatchers.IO,
    val main: CoroutineDispatcher = Dispatchers.Main,
    val unconfined: CoroutineDispatcher = Dispatchers.Unconfined,
) {
    companion object : AppDispatchers()
}