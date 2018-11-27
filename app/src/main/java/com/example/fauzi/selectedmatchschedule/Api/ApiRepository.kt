package com.example.fauzi.selectedmatchschedule.api

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.net.URL

class ApiRepository {

    fun makeRequest(url: String): Deferred<String> = GlobalScope.async {
        URL(url).readText()
    }
}