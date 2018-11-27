package com.example.fauzi.selectedmatchschedule.api

import com.example.fauzi.selectedmatchschedule.BuildConfig


object TheSportDBApi{
    fun getUri(): String{
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}"
    }

    fun getPrevLeague(): String{
        return getUri() +
                "/eventspastleague.php?id=4329"
    }

    fun getNextLeague(): String{
        return getUri() +
                "/eventsnextleague.php?id=4329"
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








}