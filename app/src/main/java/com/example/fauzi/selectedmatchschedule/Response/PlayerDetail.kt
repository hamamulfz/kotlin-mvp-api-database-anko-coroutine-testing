package com.example.fauzi.selectedmatchschedule.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayerDetail(
        @SerializedName("idPlayer")
        var playerId: String?,

        @SerializedName("strPlayer")
        var playerName: String?,

        @SerializedName("dateBorn")
        var playerDateBorn: String?,

        @SerializedName("strBirthLocation")
        var playerBirthLocation: String?,

        @SerializedName("strPosition")
        var playerPosition: String?,

        @SerializedName("strHeight")
        var playerHeight: String?,

        @SerializedName("strWeight")
        var playerWeight: String?,

        @SerializedName("strCutout")
        var playerHeadIcon: String?,

        @SerializedName("strThumb")
        var playerMainImages: String?,

        @SerializedName("strFanart1")
        var playerFanArt: String?,

/*        @SerializedName("strFacebook")
        var playerFacebook: String?,

        @SerializedName("strTwitter")
        var playerTwitter: String?,

        @SerializedName("strInstagram")
        var playerInstagram: String?,

        @SerializedName("strYoutube")
        var playerYoutube: String?,*/

        @SerializedName("strDescriptionEN")
        var playerDescription: String? = null
) : Parcelable