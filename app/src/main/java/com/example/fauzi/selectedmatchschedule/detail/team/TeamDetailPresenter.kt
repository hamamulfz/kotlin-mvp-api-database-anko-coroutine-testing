package com.example.fauzi.selectedmatchschedule.detail.team

import com.example.fauzi.selectedmatchschedule.api.ApiRepository
import com.example.fauzi.selectedmatchschedule.api.TheSportDBApi
import com.example.fauzi.selectedmatchschedule.coroutine.CoroutineContextProvider
import com.example.fauzi.selectedmatchschedule.response.PlayerListResponse
import com.example.fauzi.selectedmatchschedule.response.TeamBadgeResponse
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamDetailPresenter(private val view: TeamDetailView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson,
                          private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamDetail(teamId: String) {
        view.showLoading()

        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.getTeamDetail(teamId)).await(),
                        TeamBadgeResponse::class.java
                )


            view.showTeamDetail(data.teams)
            view.hideRV()
            view.hideLoading()
        }
    }

    fun getPlayerList(teamName: String?) {
        view.showLoading()

        GlobalScope.launch(context.main){
            val data =gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.getAllPlayer(teamName.toString())).await(),
                        PlayerListResponse::class.java
                )


            view.showPlayerList(data.player)
            view.showRV()
            view.hideLoading()
        }
    }
}