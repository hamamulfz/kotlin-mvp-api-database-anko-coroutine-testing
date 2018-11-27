package com.example.fauzi.selectedmatchschedule.MainActivity

import com.example.fauzi.selectedmatchschedule.Api.ApiRepository
import com.example.fauzi.selectedmatchschedule.Api.TheSportDBApi
import com.example.fauzi.selectedmatchschedule.Response.MatchListResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter(private val view: MainView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson) {

    fun getPrevMatchList() {
        view.startLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .makeRequest(TheSportDBApi.getPrevLeague()),
                    MatchListResponse::class.java
            )

            uiThread {
                view.loadingEnd()
                view.showMatchList(data.events)
            }
        }
    }

    fun getNextMatchList() {
        view.startLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .makeRequest(TheSportDBApi.getNextLeague()),
                    MatchListResponse::class.java
            )

            uiThread {
                view.loadingEnd()
                view.showMatchList(data.events)
            }
        }
    }




}