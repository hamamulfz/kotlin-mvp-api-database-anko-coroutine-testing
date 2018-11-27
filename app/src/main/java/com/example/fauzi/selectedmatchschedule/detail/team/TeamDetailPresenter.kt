package com.example.fauzi.selectedmatchschedule.detail.team

import com.example.fauzi.selectedmatchschedule.api.ApiRepository
import com.example.fauzi.selectedmatchschedule.api.TheSportDBApi
import com.example.fauzi.selectedmatchschedule.coroutine.CoroutineContextProvider
import com.example.fauzi.selectedmatchschedule.response.PlayerListResponse
import com.example.fauzi.selectedmatchschedule.response.TeamBadgeResponse
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamDetailPresenter(private val view: TeamDetailView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson,
                          private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamDetail(teamId: String) {
        view.showLoading()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.getTeamDetail(teamId)),
                        TeamBadgeResponse::class.java
                )
            }

            view.showTeamDetail(data.await().teams)
            view.hideRV()
            view.hideLoading()
        }
    }

    fun getPlayerList(teamName: String?) {
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.getAllPlayer(teamName.toString())),
                        PlayerListResponse::class.java
                )
            }

            view.showPlayerList(data.await().player)
            view.showRV()
            view.hideLoading()
        }
    }
}