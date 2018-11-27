package com.example.fauzi.selectedmatchschedule.detail

import com.example.fauzi.selectedmatchschedule.coroutine.CoroutineContextProvider
import com.example.fauzi.selectedmatchschedule.api.ApiRepository
import com.example.fauzi.selectedmatchschedule.api.TheSportDBApi
import com.example.fauzi.selectedmatchschedule.response.LeagueBadgeResponse
import com.example.fauzi.selectedmatchschedule.response.MatchListResponse
import com.example.fauzi.selectedmatchschedule.response.TeamBadgeResponse
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailPresenter(private val view: DetailView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()) {


    fun getBadgeHomeTeam(idHomeTeam: String?) {
        view.startLoading()
        async(context.main) {
            val dataHome = bg {
                gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.getTeamDetails(idHomeTeam.toString())),
                        TeamBadgeResponse::class.java
                )
            }
            view.showHomeTeamBadge(dataHome.await().teams)
            view.endLoading()
        }
    }

    fun getBadgeAwayTeam(idAwayTeam: String?) {
        view.startLoading()
        async(context.main) {
            val dataAway = bg {
                gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.getTeamDetails(idAwayTeam.toString())),
                        TeamBadgeResponse::class.java
                )
            }
            view.showAwayTeamBadge(dataAway.await().teams)
            view.endLoading()

        }
    }


    fun getBadgeLeague(idLeague: String?) {
        view.startLoading()
        async(context.main) {
            val dataLeague = bg {
                gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.getLeagueDetails(idLeague.toString())),
                        LeagueBadgeResponse::class.java
                )
            }
            view.showLeagueBadge(dataLeague.await().leagues)
            view.endLoading()
        }
    }

    fun getEventDetail(idEvent: String?) {
        view.startLoading()
        async(context.main){
            val dataEvent = bg {
                gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.getEventDetails(idEvent.toString())),
                        MatchListResponse::class.java
                )
            }
            view.showEventDetail(dataEvent.await().events)
            view.endLoading()
        }
    }
}


