package com.example.movietv

import com.example.movietv.utils.DateTimeConverter
import org.junit.Test

import org.junit.Assert.*

class CommonUnitTest {
    @Test
    fun MovieTvDuration_moreThanOneHour_shouldGiveExpectedOutput() {
        assertEquals("1h 35m",DateTimeConverter.convertMinutesToHourMinutes(95))
    }

    @Test
    fun MovieTvDuration_lessThanOneHour_shouldGiveExpectedOutput() {
        assertEquals("0h 22m",DateTimeConverter.convertMinutesToHourMinutes(22))
    }
}