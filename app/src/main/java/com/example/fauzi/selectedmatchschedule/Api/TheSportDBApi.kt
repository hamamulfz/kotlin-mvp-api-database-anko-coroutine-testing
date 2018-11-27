package com.example.fauzi.selectedmatchschedule.api

import com.example.fauzi.selectedmatchschedule.BuildConfig


object TheSportDBApi{
    fun getUri(): String{
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}"
    }

    fun getPrevLeague(id: String): String{
        return getUri() +
                "/eventspastleague.php?id=$id"
    }

    fun getNextLeague(id: String): String{
        return getUri() +
                "/eventsnextleague.php?id=$id"
    }

    fun getTeamDetails(id: String): String {
        return getUri() +
                "/lookupteam.php?id=$id"
    }

    fun getLeagueDetails(id: String): String {
        return getUri() +
                "/lookupleague.php?id=$id"
    }

    fun getEventDetails(id: String): String {
        return getUri() +
                "/lookupevent.php?id=$id"
    }

    fun getTeams(league: String?): String {
        return getUri() +
                "/search_all_teams.php?l=$league"
    }

    fun getTeamDetail(teamId: String?): String{
        return getUri() +
                "/lookupteam.php?id=$teamId"
    }

    fun getAllLeague(): String {
        return getUri() +
                "/all_leagues.php"
    }

    fun searchTeam(name: String): String {
        return getUri() +
                "/searchteams.php?t=$name"
    }

    fun searchMatch(name: String): String {
        return getUri() +
                "/searchevents.php?e=$name"
    }

    fun getAllPlayer(teamId: String): String {
        return getUri() +
                "/lookup_all_players.php?id=$teamId"
    }

    fun getPlayerDetail(playerId: String): String {
        return getUri() +
                "/lookupplayer.php?id=$playerId"
    }










}