package com.example.fauzi.selectedmatchschedule.detail.match

import com.example.fauzi.selectedmatchschedule.coroutine.CoroutineContextProvider
import com.example.fauzi.selectedmatchschedule.api.ApiRepository
import com.example.fauzi.selectedmatchschedule.api.TheSportDBApi
import com.example.fauzi.selectedmatchschedule.response.LeagueBadgeResponse
import com.example.fauzi.selectedmatchschedule.response.MatchListResponse
import com.example.fauzi.selectedmatchschedule.response.TeamBadgeResponse
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailPresenter(private val view: DetailView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()) {


    fun getBadgeHomeTeam(idHomeTeam: String?) {
        view.startLoading()
        GlobalScope.launch(context.main){
            val dataHome = gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.getTeamDetails(idHomeTeam.toString())).await(),
                        TeamBadgeResponse::class.java
                )

            view.showHomeTeamBadge(dataHome.teams)
            view.endLoading()
        }
    }

    fun getBadgeAwayTeam(idAwayTeam: String?) {
        view.startLoading()
        GlobalScope.launch(context.main){
            val dataAway = gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.getTeamDetails(idAwayTeam.toString())).await(),
                        TeamBadgeResponse::class.java
                )

            view.showAwayTeamBadge(dataAway.teams)
            view.endLoading()

        }
    }


    fun getBadgeLeague(idLeague: String?) {
        view.startLoading()
        GlobalScope.launch(context.main){
            val dataLeague = gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.getLeagueDetails(idLeague.toString())).await(),
                        LeagueBadgeResponse::class.java
                )

            view.showLeagueBadge(dataLeague.leagues)
            view.endLoading()
        }
    }

    fun getEventDetail(idEvent: String?) {
        view.startLoading()
        GlobalScope.launch(context.main){
            val dataEvent = gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.getEventDetails(idEvent.toString())).await(),
                        MatchListResponse::class.java
            )

            view.showEventDetail(dataEvent.events)
            view.endLoading()
        }
    }
}


