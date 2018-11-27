package com.example.fauzi.selectedmatchschedule.main

import com.example.fauzi.selectedmatchschedule.coroutine.CoroutineContextProvider
import com.example.fauzi.selectedmatchschedule.api.ApiRepository
import com.example.fauzi.selectedmatchschedule.api.TheSportDBApi
import com.example.fauzi.selectedmatchschedule.response.MatchListResponse
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MainPresenter(private val view: MainView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPrevMatchList() {
        view.startLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.getPrevLeague()),
                        MatchListResponse::class.java
                )
            }
            view.showMatchList(data.await().events)
            view.loadingEnd()
        }
    }

    fun getNextMatchList() {
        view.startLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.getNextLeague()),
                        MatchListResponse::class.java
                )
            }
                view.loadingEnd()
                view.showMatchList(data.await().events)
        }
    }
}