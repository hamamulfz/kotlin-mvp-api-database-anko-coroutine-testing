package com.example.fauzi.selectedmatchschedule.detail.match

import com.example.fauzi.selectedmatchschedule.response.LeagueBadge
import com.example.fauzi.selectedmatchschedule.response.MatchList
import com.example.fauzi.selectedmatchschedule.response.TeamBadge

interface DetailView {

    fun startLoading()
    fun endLoading()
    fun showHomeTeamBadge(dataHomeTeam: List<TeamBadge>)
    fun showAwayTeamBadge(dataAwayTeam: List<TeamBadge>)
    fun showLeagueBadge(dataLeague: List<LeagueBadge>)
    fun showEventDetail(dataEvents: List<MatchList>)

}
