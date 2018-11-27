package com.example.fauzi.selectedmatchschedule.Api

import java.net.URL

class ApiRepository {

    fun makeRequest(url: String): String {
        return URL(url).readText()
    }
}