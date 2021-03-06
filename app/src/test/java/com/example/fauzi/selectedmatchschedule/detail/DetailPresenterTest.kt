package com.example.fauzi.selectedmatchschedule.detail

import com.example.fauzi.selectedmatchschedule.coroutine.TestContextProvider
import com.example.fauzi.selectedmatchschedule.api.ApiRepository
import com.example.fauzi.selectedmatchschedule.api.TheSportDBApi
import com.example.fauzi.selectedmatchschedule.detail.match.DetailPresenter
import com.example.fauzi.selectedmatchschedule.detail.match.DetailView
import com.example.fauzi.selectedmatchschedule.response.*
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class DetailPresenterTest {

    @Mock
    private
    lateinit var view: DetailView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: DetailPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = DetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetBadgeHomeTeam() {
        val dataTeams: MutableList<TeamBadge> = mutableListOf()
        val response = TeamBadgeResponse(dataTeams)
        val id = "133621" //Bristol City

        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository
                    .makeRequest(TheSportDBApi.getTeamDetails(id)).await(),
                    TeamBadgeResponse::class.java
            )).thenReturn(response)

            presenter.getBadgeHomeTeam(id)

            verify(view).startLoading()
            verify(view).showHomeTeamBadge(dataTeams)
            verify(view).endLoading()
        }

    }

    @Test
    fun testGetBadgeAwayTeam() {
        val dataTeams: MutableList<TeamBadge> = mutableListOf()
        val response = TeamBadgeResponse(dataTeams)
        val id = "133809" //Preston

        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository
                    .makeRequest(TheSportDBApi.getTeamDetails(id)).await(),
                    TeamBadgeResponse::class.java
            )).thenReturn(response)

            presenter.getBadgeAwayTeam(id)

            verify(view).startLoading()
            verify(view).showAwayTeamBadge(dataTeams)
            verify(view).endLoading()
        }

    }

    @Test
    fun testGetBadgeLeague() {
        val dataTeams: MutableList<LeagueBadge> = mutableListOf()
        val response = LeagueBadgeResponse(dataTeams)
        val id = "4329" //English League Championship

        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository
                    .makeRequest(TheSportDBApi.getLeagueDetails(id)).await(),
                    LeagueBadgeResponse::class.java
            )).thenReturn(response)

            presenter.getBadgeLeague(id)

            verify(view).startLoading()
            verify(view).showLeagueBadge(dataTeams)
            verify(view).endLoading()
        }

    }

    @Test
    fun testGetEventDetail() {
        val dataEvents: MutableList<MatchList> = mutableListOf()
        val response = MatchListResponse(dataEvents)
        val id = "577654" //Bristol City vs Preston

        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository
                    .makeRequest(TheSportDBApi.getEventDetails(id)).await(),
                    MatchListResponse::class.java
            )).thenReturn(response)

            presenter.getEventDetail(id)

            verify(view).startLoading()
            verify(view).showEventDetail(dataEvents)
            verify(view).endLoading()
        }
    }
}