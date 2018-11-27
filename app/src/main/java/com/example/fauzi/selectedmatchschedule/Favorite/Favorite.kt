package com.example.fauzi.selectedmatchschedule.Favorite

data class Favorite(val id: Long?,
                    val eventId: String?,
                    val leagueId: String?,
                    val homeTeamId: String?,
                    val awayTeamId: String?,
                    val dateOfMatch: String?,
                    val homeScore: String?,
                    val awayScore: String?,
                    val awayTeamVsHomeTeam: String?
                    ) {
    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val ID_EVENT: String =  "ID_EVENT"
        const val LEAGUE_ID: String = "LEAGUE_ID"
        const val HOME_ID: String = "HOME_ID"
        const val AWAY_ID: String = "AWAY_ID"
        const val DATE: String = "DATE"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val HOME_VS_AWAY : String = "HOME_VS_AWAY"
    }
}