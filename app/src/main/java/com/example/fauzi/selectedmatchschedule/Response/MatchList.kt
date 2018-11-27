package com.example.fauzi.selectedmatchschedule.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MatchList(

        //in Main adn Detail Activity
        @SerializedName("idEvent")
        var eventId: String?,
        @SerializedName("dateEvent")
        var dateOfMatch: String?,
        @SerializedName("idAwayTeam")
        var awayTeamId: String,
        @SerializedName("idHomeTeam")
        var homeTeamId: String,
        @SerializedName("intHomeScore")
        var homeScore: String?,
        @SerializedName("intAwayScore")
        var awayScore: String?,
        @SerializedName("strEvent")
        var awayTeamVsHomeTeam: String?,

        //in detail activyty
        @SerializedName("idLeague")
        var leagueId: String?,
        @SerializedName("strLeague")
        var leagueName: String?,

        @SerializedName("strAwayFormation")
        var awayTeamFormation: String?,
        @SerializedName("strAwayTeam")
        var awayTeamName: String?,
        @SerializedName("strAwayGoalDetails")
        var awayTeamGoalDetails: String?,
        @SerializedName("strAwayLineupDefense")
        var awayTeamLineupDefense: String?,
        @SerializedName("strAwayLineupForward")
        var awayTeamLineupForward: String?,
        @SerializedName("strAwayLineupGoalkeeper")
        var awayTeamLineupGoalkeeper: String?,
        @SerializedName("strAwayLineupMidfield")
        var awayTeamLineupMidfield: String?,
        @SerializedName("strAwayLineupSubstitutes")
        var awayTeamLineupSubstitutes: String?,
        @SerializedName("strAwayRedCards")
        var awayTeamRedCards: String?,
        @SerializedName("strAwayYellowCards")
        var awayTeamYellowCards: String?,

        @SerializedName("strHomeTeam")
        var homeTeamName: String?,
        @SerializedName("strHomeGoalDetails")
        var homeTeamGoalDetails: String?,
        @SerializedName("strHomeFormation")
        var homeTeamFormation: String?,
        @SerializedName("strHomeLineupDefense")
        var homeTeamLineupDefense: String?,
        @SerializedName("strHomeLineupForward")
        var homeTeamLineupForward: String?,
        @SerializedName("strHomeLineupGoalkeeper")
        var homeTeamLineupGoalkeeper: String?,
        @SerializedName("strHomeLineupMidfield")
        var homeTeamLineupMidfield: String?,
        @SerializedName("strHomeLineupSubstitutes")
        var homeTeamLineupSubstitutes: String?,
        @SerializedName("strHomeRedCards")
        var homeTeamRedCards: String?,
        @SerializedName("strHomeYellowCards")
        var homeTeamYellowCards: String?

) : Parcelable