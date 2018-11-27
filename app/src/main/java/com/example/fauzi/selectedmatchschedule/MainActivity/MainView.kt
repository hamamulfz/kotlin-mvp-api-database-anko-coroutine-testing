package com.example.fauzi.selectedmatchschedule.MainActivity

import com.example.fauzi.selectedmatchschedule.Response.MatchList

interface MainView {

    fun startLoading()
    fun loadingEnd()
    fun showMatchList(data: List<MatchList>)
}