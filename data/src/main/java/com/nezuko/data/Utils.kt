package com.nezuko.data

import android.util.Log
import com.nezuko.domain.model.ResultModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.request
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

suspend inline fun <reified T> HttpClient.requestGet(
    urlString: String,
    block: HttpRequestBuilder.() -> Unit = {},
    logTag: String = "HttpClient.requestGet"
): ResultModel<T> {
    try {
        val response = get(urlString, block)
        Log.i("HttpClient.requestGet", "$logTag: ${response.request.url}")
        Log.i("HttpClient.requestGet", "$logTag: ${response.request.headers}")
        if (response.status.value in 200..299) {
            val TResponse = response.body<T>()
            Log.i("HttpClient.requestGet", "$logTag: $TResponse")
            return ResultModel.success(TResponse)
        } else {
            Log.e("HttpClient.requestGet", "$logTag: code - ${response.status.value}")
            Log.i("HttpClient.requestGet", "$logTag: ${response.body<String>()}")
            return ResultModel.failure("$logTag - StatusC0de - ${response.status.value}")
        }
    } catch (e: Exception) {
        Log.e("HttpClient.requestGet", "$logTag: error", e)
        return ResultModel.failure(e.message)
    }
}

suspend inline fun <reified T> HttpClient.requestPost(
    urlString: String,
    block: HttpRequestBuilder.() -> Unit,
    logTag: String
): ResultModel<T> {
    try {
        val response = post(urlString, block)
        if (response.status.value in 200..299) {
            val TResponse = response.body<T>()
            Log.i(logTag, "$logTag: $TResponse")
            return ResultModel.success(TResponse)
        } else {
            Log.e(logTag, "$logTag: code - ${response.status.value}")
            Log.i(logTag, "$logTag: ${response.body<String>()}")
            return ResultModel.failure("$logTag - StatusC0de - ${response.status.value}")
        }
    } catch (e: Exception) {
        Log.e(logTag, "$logTag: error", e)
        return ResultModel.failure(e.message)
    }
}

fun parseDate(dateString: String): LocalDate {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    val zonedDateTime = ZonedDateTime.parse(dateString, formatter)
    return zonedDateTime.toLocalDate()
}