package com.example.fauzi.selectedmatchschedule.list.team

import com.example.fauzi.selectedmatchschedule.api.ApiRepository
import com.example.fauzi.selectedmatchschedule.api.TheSportDBApi
import com.example.fauzi.selectedmatchschedule.coroutine.CoroutineContextProvider
import com.example.fauzi.selectedmatchschedule.response.LeagueBadgeResponse
import com.example.fauzi.selectedmatchschedule.response.TeamBadgeResponse
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamPresenter(private val view: TeamView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamList(league: String?) {
        view.showLoading()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.getTeams(league.toString())),
                        TeamBadgeResponse::class.java
                )
            }

            view.showTeamList(data.await().teams)
            view.hideLoading()

        }
    }

    fun searchTeam(name: String?) {
        view.showLoading()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.searchTeam(name.toString())),
                        TeamBadgeResponse::class.java
                )
            }
            view.showTeamList(data.await().teams)
            view.hideLoading()

        }
    }

    fun getAllLeagueName() {
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.getAllLeague()),
                        LeagueBadgeResponse::class.java
                )
            }

            view.hideLoading()
            view.showLeagueList(data.await().leagues)
        }
    }
}