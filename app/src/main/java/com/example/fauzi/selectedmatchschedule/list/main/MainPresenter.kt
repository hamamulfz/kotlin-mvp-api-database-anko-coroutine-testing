package com.example.fauzi.selectedmatchschedule.list.main

import com.example.fauzi.selectedmatchschedule.coroutine.CoroutineContextProvider
import com.example.fauzi.selectedmatchschedule.api.ApiRepository
import com.example.fauzi.selectedmatchschedule.api.TheSportDBApi
import com.example.fauzi.selectedmatchschedule.response.MatchListResponse
import com.example.fauzi.selectedmatchschedule.response.MatchListSearchResponse
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainPresenter(private val view: MainView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPrevMatchList(league: String?) {
        view.startLoading()

        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.getPrevLeague(league.toString())).await(),
                        MatchListResponse::class.java
                )

            view.showMatchList(data.events)
            view.loadingEnd()
        }
    }

    fun getNextMatchList(league: String?) {
        view.startLoading()
        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.getNextLeague(league.toString())).await(),
                        MatchListResponse::class.java
                )

                view.loadingEnd()
                view.showMatchList(data.events)
        }
    }

    fun searchMatchList(name: String?) {
        view.startLoading()
        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.searchMatch(name.toString())).await(),
                        MatchListSearchResponse::class.java
                )

            view.loadingEnd()
            view.showMatchList(data.event)
        }
    }
}