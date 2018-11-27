package com.example.fauzi.selectedmatchschedule.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamBadge(
        @SerializedName("strTeamBadge")
        var matchTeamBadge: String?,

        @SerializedName("idTeam")
        var teamId: String? = null,

        @SerializedName("strTeam")
        var teamName: String? = null,

        @SerializedName("intFormedYear")
        var teamFormedYear: String? = null,

        @SerializedName("strStadium")
        var teamStadium: String? = null,

        @SerializedName("strDescriptionEN")
        var teamDescription: String? = null
        ) : Parcelable