package com.example.fauzi.selectedmatchschedule.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun dateConvertion(date: String?): String? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val newDate = dateFormat.parse(date)
    return SimpleDateFormat("EEE, dd MMM yyy").format(newDate)
}

fun spaceConvertion(string: String): String {
    val convertString = string.replace(" ", "%20")
    return convertString
}

fun convertToId(leagueString: String): String {
     return when (leagueString) {
        "English Premier League" -> "4328"
        "English League Championship" -> "4329"
        "Scottish Premier League" -> "4330"
        "German Bundesliga" -> "4331"
        "Italian Serie A" -> "4332"
        "French Ligue 1" -> "4334"
        "Spanish La Liga" -> "4335"
        "Greek Superleague Greece" -> "4336"
        "Dutch Eredivisie" -> "4337"
        "Belgian Jupiler League" -> "4338"
        "Danish Superliga" -> "4340"
        "Portuguese Primeira Liga" -> "4344"
        "American Major League Soccer" -> "4346"
        "Swedish Allsvenskan" -> "4347"
        "Australian A-League" -> "4356"
         else -> ""
    }
}

fun toGMTFormat(date: String, time: String): Date? {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss+00:00")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    val dateTime = "$date $time"
    return formatter.parse(dateTime)
}

fun remove(string: String): CharSequence {
    return string.subSequence(11,16)
}

fun extractTime(date: String, time: String): String {
    val string= toGMTFormat(date, time)
    val timed = remove("$string")
    return "$timed WIB"

}
