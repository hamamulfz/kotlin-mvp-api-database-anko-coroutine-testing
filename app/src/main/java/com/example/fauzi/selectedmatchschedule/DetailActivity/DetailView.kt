package com.example.fauzi.selectedmatchschedule.DetailActivity

import com.example.fauzi.selectedmatchschedule.Response.LeagueBadge
import com.example.fauzi.selectedmatchschedule.Response.TeamBadge

interface DetailView {

    fun startLoading()
    fun endLoading()
    fun showHomeTeamBadge(dataHomeTeam: List<TeamBadge>)
    fun showAwayTeamBadge(dataAwayTeam: List<TeamBadge>)
    fun showLeagueBadge(dataLeague: List<LeagueBadge>) {

    }
}
