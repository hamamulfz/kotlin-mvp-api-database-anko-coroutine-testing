package com.example.fauzi.selectedmatchschedule.list.main

import com.example.fauzi.selectedmatchschedule.coroutine.CoroutineContextProvider
import com.example.fauzi.selectedmatchschedule.api.ApiRepository
import com.example.fauzi.selectedmatchschedule.api.TheSportDBApi
import com.example.fauzi.selectedmatchschedule.response.MatchListResponse
import com.example.fauzi.selectedmatchschedule.response.MatchListSearchResponse
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MainPresenter(private val view: MainView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPrevMatchList(league: String?) {
        view.startLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.getPrevLeague(league.toString())),
                        MatchListResponse::class.java
                )
            }
            view.showMatchList(data.await().events)
            view.loadingEnd()
        }
    }

    fun getNextMatchList(league: String?) {
        view.startLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.getNextLeague(league.toString())),
                        MatchListResponse::class.java
                )
            }
                view.loadingEnd()
                view.showMatchList(data.await().events)
        }
    }

    fun searchMatchList(name: String?) {
        view.startLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.searchMatch(name.toString())),
                        MatchListSearchResponse::class.java
                )
            }
            view.loadingEnd()
            view.showMatchList(data.await().event)
        }
    }
}