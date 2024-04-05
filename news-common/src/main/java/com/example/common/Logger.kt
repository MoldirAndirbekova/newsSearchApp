package com.example.common

import android.util.Log
import java.security.MessageDigest

interface Logger {

    fun d(tag: String, messageDigest: String)
    fun e(tag: String, messageDigest: String)

}

fun AndroidLogcatLogger(): Logger = object : Logger {
    override fun d(tag: String, message: String) {
        Log.d(tag,message)
    }
    override fun e(tag: String, message: String) {
        Log.d(tag,message)
    }
}