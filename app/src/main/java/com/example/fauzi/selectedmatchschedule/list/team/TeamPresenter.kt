package com.example.fauzi.selectedmatchschedule.list.team

import com.example.fauzi.selectedmatchschedule.api.ApiRepository
import com.example.fauzi.selectedmatchschedule.api.TheSportDBApi
import com.example.fauzi.selectedmatchschedule.coroutine.CoroutineContextProvider
import com.example.fauzi.selectedmatchschedule.response.TeamBadgeResponse
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamPresenter(private val view: TeamView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamList(league: String?) {
        view.showLoading()

        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.getTeams(league.toString())).await(),
                        TeamBadgeResponse::class.java
                )


            view.showTeamList(data.teams)
            view.hideLoading()

        }
    }

    fun searchTeam(name: String?) {
        view.showLoading()

        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.searchTeam(name.toString())).await(),
                        TeamBadgeResponse::class.java
                )

            view.showTeamList(data.teams)
            view.hideLoading()

        }
    }
}