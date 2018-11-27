package com.example.fauzi.selectedmatchschedule.utils

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*

class ConvertKtTest {

    @Test
    fun tesToSimpleString() {
        val date = "2018-02-28"
        assertEquals("Wed, 28 Feb 2018", dateConvertion(date))
    }

    @Test
    fun tesConvertString() {
        val string = "Satu Dua"
        assertEquals("Satu%20Dua", spaceConvertion(string))
    }

    @Test
    fun tesConvertToId() {
        val string = "English Premier League"
        assertEquals("4328", convertToId(string))
    }

    @Test
    fun tesToGMTFormat() {
        val date1 = "2018-11-18"
        val time = "02:06:00+00:00"

        val string= toGMTFormat(date1, time)
        System.out.println(string)
        val cut = extractTime(date1, time)
        System.out.println("$cut" )

    }


}