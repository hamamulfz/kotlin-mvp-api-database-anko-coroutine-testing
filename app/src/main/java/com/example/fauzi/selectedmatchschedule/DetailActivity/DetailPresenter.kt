package com.example.fauzi.selectedmatchschedule.DetailActivity

import com.example.fauzi.selectedmatchschedule.Api.ApiRepository
import com.example.fauzi.selectedmatchschedule.Api.TheSportDBApi
import com.example.fauzi.selectedmatchschedule.Response.LeagueBadgeResponse
import com.example.fauzi.selectedmatchschedule.Response.TeamBadgeResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailPresenter(private val view: DetailView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson) {


    fun getBadgeHomeTeam(idHomeTeam: String?) {
        view.startLoading()
        doAsync {
            val dataHome = gson.fromJson(apiRepository
                    .makeRequest(TheSportDBApi.getTeamDetails(idHomeTeam.toString())),
                    TeamBadgeResponse::class.java
            )
            uiThread {
                view.endLoading()
                view.showHomeTeamBadge(dataHome.teams)
            }
        }
    }

    fun getBadgeAwayTeam(idAwayTeam: String?) {
        view.startLoading()
        doAsync {
            val dataAway = gson.fromJson(apiRepository
                    .makeRequest(TheSportDBApi.getTeamDetails(idAwayTeam.toString())),
                    TeamBadgeResponse::class.java
            )
            uiThread {
                view.endLoading()
                view.showAwayTeamBadge(dataAway.teams)
            }
        }
    }


    fun getBadgeLeague(idLeague: String?) {
        view.startLoading()
        doAsync {
            val dataLeague = gson.fromJson(apiRepository
                    .makeRequest(TheSportDBApi.getLeagueDetails(idLeague.toString())),
                    LeagueBadgeResponse::class.java
            )
            uiThread {
                view.endLoading()
                view.showLeagueBadge(dataLeague.leagues)
            }
        }
    }
}


