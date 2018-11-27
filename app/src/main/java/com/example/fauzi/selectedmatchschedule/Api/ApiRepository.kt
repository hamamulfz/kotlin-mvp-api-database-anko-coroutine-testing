package com.example.fauzi.selectedmatchschedule.api

import java.net.URL

class ApiRepository {

    fun makeRequest(url: String): String {
        return URL(url).readText()
    }
}