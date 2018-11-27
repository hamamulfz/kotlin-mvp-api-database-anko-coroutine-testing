package com.example.fauzi.selectedmatchschedule.list.main

import com.example.fauzi.selectedmatchschedule.response.MatchList

interface MainView {

    fun startLoading()
    fun loadingEnd()
    fun showMatchList(data: List<MatchList>)
}