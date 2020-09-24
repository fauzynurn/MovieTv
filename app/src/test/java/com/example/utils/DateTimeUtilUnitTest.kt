package com.example.utils

import com.example.movietv.utils.DateTimeUtil
import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat

class DateTimeUtilUnitTest {
    @Test
    fun MovieTvDuration_moreThanOneHour_shouldGiveExpectedOutput() {
        assertEquals("1h 35m",DateTimeUtil.convertMinutesToHourMinutes(95))
    }

    @Test
    fun MovieTvDuration_lessThanOneHour_shouldGiveExpectedOutput() {
        assertEquals("0h 22m",DateTimeUtil.convertMinutesToHourMinutes(22))
    }

    @Test
    fun GetYear_YYYYMMdd_shouldReturnCorrectYear(){
        assertEquals(2020,DateTimeUtil.getYear(SimpleDateFormat("yyyy-MM-dd"),"2020-09-10"))
    }

    @Test
    fun GetDayDifference_shouldReturnCorrectDay(){
        assertEquals(2,DateTimeUtil.getCurrentDiffWith(SimpleDateFormat("yyyy-MM-dd"),"2020-09-25"))
    }
}