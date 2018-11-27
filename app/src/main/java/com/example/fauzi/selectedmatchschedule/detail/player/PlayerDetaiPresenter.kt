package com.example.fauzi.selectedmatchschedule.detail.player

import com.example.fauzi.selectedmatchschedule.api.ApiRepository
import com.example.fauzi.selectedmatchschedule.api.TheSportDBApi
import com.example.fauzi.selectedmatchschedule.coroutine.CoroutineContextProvider
import com.example.fauzi.selectedmatchschedule.response.PlayerDetailResponse
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerDetaiPresenter(private val view: PlayerDetailView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson,
                          private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPlayerDetail(playerId: String) {
        view.showLoading()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .makeRequest(TheSportDBApi.getPlayerDetail(playerId)),
                        PlayerDetailResponse::class.java
                )
            }

            view.showPlayerDetail(data.await().players)
            view.hideLoading()
        }
    }


}