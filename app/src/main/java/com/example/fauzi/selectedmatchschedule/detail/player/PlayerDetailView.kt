package com.example.fauzi.selectedmatchschedule.detail.player

import com.example.fauzi.selectedmatchschedule.response.PlayerDetail

interface PlayerDetailView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerDetail(data: List<PlayerDetail>)
}