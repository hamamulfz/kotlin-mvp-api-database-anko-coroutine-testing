package com.example.fauzi.selectedmatchschedule.detail.team

import com.example.fauzi.selectedmatchschedule.response.PlayerDetail
import com.example.fauzi.selectedmatchschedule.response.TeamBadge

interface TeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(data: List<TeamBadge>)
    fun showPlayerList (data: List<PlayerDetail>)
    fun showRV()
    fun hideRV()
}