package org.example.project.network

import kotlinx.coroutines.flow.Flow
import org.example.project.network.models.*


interface F1Api {
    fun getCarData(
        date: String? = null,
        driverNumber: Int? = null,
        meetingKey: String? = null,
        sessionKey: String? = null,

        ): Flow<List<CarData>>

    fun getDrivers(
        driverNumber: Int? = null,
        meetingKey: String? = null,
        sessionKey: String? = null,
    ): Flow<List<Drivers>>

    fun getIntervals(
        sessionKey: String? = null,
        meetingKey: String? = null,
        driverNumber: Int? = null,
    ): Flow<List<Intervals>>

    fun getLaps(
        sessionKey: String? = null,
        meetingKey: String? = null,
        driverNumber: Int? = null,
        lapNumber: Int? = null
    ): Flow<List<Laps>>

    suspend fun getSessions(
        sessionKey: String? = null,
        meetingKey: String? = null,
        sessionName: String? = null,
        countryName: String? = null,
        circuitShortName: String? = null,
        year: Int? = null,
    ): List<Sessions>

    fun getLocations(
        date: String? = null,
        driverNumber: Int? = null,
        sessionKey: String? = null,
        meetingKey: String? = null,
    ): Flow<List<Location>>

    suspend fun getMeetings(
        year: Int? = null,
        countryName: String? = null,
        location: String? = null,
    ): List<Meetings>

    fun getPit(
        driverNumber: Int? = null,
        sessionKey: String? = null,
        meetingKey: String? = null,
    ): Flow<List<Pit>>

    fun getPosition(
        driverNumber: Int? = null,
        sessionKey: String? = null,
        meetingKey: String? = null,
    ): Flow<List<Position>>

    fun getRaceControl(
        driverNumber: Int? = null,
        sessionKey: String? = null,
        meetingKey: String? = null,
    ): Flow<List<RaceControl>>

    fun getStints(
        driverNumber: Int? = null,
        sessionKey: String? = null,
        meetingKey: String? = null,
    ): Flow<List<Stints>>

    fun getTeamRadios(
        driverNumber: Int? = null,
        sessionKey: String? = null,
        meetingKey: String? = null,
    ): Flow<List<TeamRadio>>

    fun getWeather(
        sessionKey: String? = null,
        meetingKey: String? = null,
    ): Flow<List<Weather>>

   suspend fun refresh()


}