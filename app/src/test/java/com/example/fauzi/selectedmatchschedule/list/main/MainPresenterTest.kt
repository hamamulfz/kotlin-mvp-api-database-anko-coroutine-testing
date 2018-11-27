package com.example.fauzi.selectedmatchschedule.list.main

import com.example.fauzi.selectedmatchschedule.coroutine.TestContextProvider
import com.example.fauzi.selectedmatchschedule.api.ApiRepository
import com.example.fauzi.selectedmatchschedule.api.TheSportDBApi
import com.example.fauzi.selectedmatchschedule.response.MatchList
import com.example.fauzi.selectedmatchschedule.response.MatchListResponse
import com.example.fauzi.selectedmatchschedule.utils.convertToId
import com.google.gson.Gson
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MainPresenterTest {

    @Mock
    private
    lateinit var view: MainView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MainPresenter
    private val string = "English Premier League"

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetPrevMatchList() {
        val dataEvents: MutableList<MatchList> = mutableListOf()
        val response = MatchListResponse(dataEvents)

        `when`(gson.fromJson(apiRepository
                .makeRequest(TheSportDBApi.getPrevLeague(convertToId(string))),
                MatchListResponse::class.java
        )).thenReturn(response)

        presenter.getPrevMatchList(convertToId(string))

        verify(view).startLoading()
        verify(view).showMatchList(dataEvents)
        verify(view).loadingEnd()
    }

    @Test
    fun testGetNextMatchList() {
        val events: MutableList<MatchList> = mutableListOf()
        val response= MatchListResponse(events)

        `when`(gson.fromJson(apiRepository
                .makeRequest(TheSportDBApi.getNextLeague(convertToId(string))),
                MatchListResponse::class.java
        )).thenReturn(response)

        presenter.getPrevMatchList(convertToId(string))

        verify(view).startLoading()
        verify(view).showMatchList(events)
        verify(view).loadingEnd()
    }
}
