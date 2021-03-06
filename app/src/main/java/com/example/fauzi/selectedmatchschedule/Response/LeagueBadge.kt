package com.example.fauzi.selectedmatchschedule.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LeagueBadge(
        @SerializedName("strBadge")
        var leagueBadgeUrl: String?,

        @SerializedName("strLeague")
        var leagueListName: String?


) : Parcelable