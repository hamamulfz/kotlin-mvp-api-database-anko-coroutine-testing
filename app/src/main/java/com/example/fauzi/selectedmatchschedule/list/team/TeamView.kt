package com.example.fauzi.selectedmatchschedule.list.team

import com.example.fauzi.selectedmatchschedule.response.LeagueBadge
import com.example.fauzi.selectedmatchschedule.response.TeamBadge

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<TeamBadge>)
    fun showLeagueList(data: List<LeagueBadge>)
}