package com.example.fauzi.selectedmatchschedule.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamBadge(
        @SerializedName("strTeamBadge")
        var matchTeamBadge: String?
        ) : Parcelable