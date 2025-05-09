package org.example.project.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.Json
import org.example.project.network.models.*
import kotlin.coroutines.coroutineContext

class F1ApiClient : F1Api {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    coerceInputValues = true
                    explicitNulls = false
                })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 10000
        }

    }

    companion object {
        private const val BASE_URL = "https://api.openf1.org/v1"
        private const val DELAY_TIME = 500L
    }

    override fun getCarData(
        date: String?,
        driverNumber: Int?,
        meetingKey: String?,
        sessionKey: String?,
    ): Flow<List<CarData>> = flow {
        while (coroutineContext.isActive) {
            val request: List<CarData> = makeRequest("car_data") {
                date?.let { parameters.append("date", it) }
                driverNumber?.let { parameters.append("driver_number", it.toString()) }
                meetingKey?.let { parameters.append("meeting_key", it) }
                sessionKey?.let { parameters.append("session_key", it) }
            }
            emit(request)
            delay(DELAY_TIME)
        }
    }

    override fun getDrivers(
        driverNumber: Int?,
        meetingKey: String?,
        sessionKey: String?,
    ): Flow<List<Drivers>> = flow {
        while (coroutineContext.isActive) {
            val request: List<Drivers> = makeRequest("drivers") {
                driverNumber?.let { parameters.append("driver_number", it.toString()) }
                meetingKey?.let { parameters.append("meeting_key", it) }
                sessionKey?.let { parameters.append("session_key", it) }
            }
            emit(request)
            delay(DELAY_TIME)
            if (!coroutineContext.isActive) {
                println("Terminando el flujo de datos")
            }
        }
    }

    override fun getIntervals(
        sessionKey: String?,
        meetingKey: String?,
        driverNumber: Int?,
    ): Flow<List<Intervals>> = flow {
        while (coroutineContext.isActive) {
            val request: List<Intervals> = makeRequest("intervals") {
                sessionKey?.let { parameters.append("session_key", it) }
                meetingKey?.let { parameters.append("meeting_key", it) }
                driverNumber?.let { parameters.append("driver_number", it.toString()) }
            }
            emit(request)
            delay(DELAY_TIME)
            if (!coroutineContext.isActive) {
                println("Terminando el flujo de datos")
            }
        }
    }


    override fun getLaps(
        sessionKey: String?, meetingKey: String?, driverNumber: Int?, lapNumber: Int?
    ): Flow<List<Laps>> = flow {
        while (coroutineContext.isActive) {
            val request: List<Laps> = makeRequest("laps") {
                sessionKey?.let { parameters.append("session_key", it) }
                meetingKey?.let { parameters.append("meeting_key", it) }
                driverNumber?.let { parameters.append("driver_number", it.toString()) }
                lapNumber?.let { parameters.append("lap_number", it.toString()) }
            }
            emit(request)
            delay(DELAY_TIME)
            if (!coroutineContext.isActive) {
                println("Terminando el flujo de datos")
            }
        }
    }

    override fun getLocations(
        date: String?, driverNumber: Int?, sessionKey: String?, meetingKey: String?
    ): Flow<List<Location>> = flow {
        while (coroutineContext.isActive) {
            val request: List<Location> = makeRequest("location") {
                date?.let { parameters.append("date", it) }
                driverNumber?.let { parameters.append("driver_number", it.toString()) }
                sessionKey?.let { parameters.append("session_key", it) }
                meetingKey?.let { parameters.append("meeting_key", it) }
            }
            emit(request)
            delay(DELAY_TIME)
            if (!coroutineContext.isActive) {
                println("Terminando el flujo de datos")
            }
        }
    }

    override suspend fun getSessions(
        sessionKey: String?,
        meetingKey: String?,
        sessionName: String?,
        countryName: String?,
        circuitShortName: String?,
        year: Int?
    ): List<Sessions> = makeRequest("sessions") {
        sessionKey?.let { parameters.append("session_key", it) }
        meetingKey?.let { parameters.append("meeting_key", it) }
        sessionName?.let { parameters.append("session_name", it) }
        countryName?.let { parameters.append("country_name", it) }
        circuitShortName?.let { parameters.append("circuit_short_name", it) }
        year?.let { parameters.append("year", it.toString()) }

    }


    override suspend fun getMeetings(
        year: Int?,
        countryName: String?,
        location: String?,
    ): List<Meetings> = makeRequest("meetings") {
        year?.let { parameters.append("year", it.toString()) }
        countryName?.let { parameters.append("country_name", it) }
        location?.let { parameters.append("location", it) }
    }


    override fun getPit(
        driverNumber: Int?,
        sessionKey: String?,
        meetingKey: String?,
    ): Flow<List<Pit>> = flow {
        while (coroutineContext.isActive) {
            val request: List<Pit> = makeRequest("pit") {
                sessionKey?.let { parameters.append("session_key", it) }
                meetingKey?.let { parameters.append("meeting_key", it) }
                driverNumber?.let { parameters.append("driver_number", it.toString()) }
            }
            emit(request)
            delay(DELAY_TIME)

        }
    }


    override fun getPosition(
        driverNumber: Int?, sessionKey: String?, meetingKey: String?
    ): Flow<List<Position>> = flow {
        while (coroutineContext.isActive) {
            val request: List<Position> = makeRequest("position") {
                sessionKey?.let { parameters.append("session_key", it) }
                meetingKey?.let { parameters.append("meeting_key", it) }
                driverNumber?.let { parameters.append("driver_number", it.toString()) }
            }
            emit(request)
            delay(DELAY_TIME)
        }
    }

    override fun getRaceControl(
        driverNumber: Int?, sessionKey: String?, meetingKey: String?
    ): Flow<List<RaceControl>> = flow {
        while (coroutineContext.isActive) {
            val request: List<RaceControl> = makeRequest("race_control") {
                sessionKey?.let { parameters.append("session_key", it) }
                meetingKey?.let { parameters.append("meeting_key", it) }
                driverNumber?.let { parameters.append("driver_number", it.toString()) }

            }
            emit(request)
            delay(DELAY_TIME)
        }
    }

    override fun getStints(
        driverNumber: Int?, sessionKey: String?, meetingKey: String?
    ): Flow<List<Stints>> = flow {
        while (coroutineContext.isActive) {
            val request: List<Stints> = makeRequest("stints") {
                sessionKey?.let { parameters.append("session_key", it) }
                meetingKey?.let { parameters.append("meeting_key", it) }
                driverNumber?.let { parameters.append("driver_number", it.toString()) }
            }
            emit(request)
            delay(DELAY_TIME)
        }
    }

    override fun getTeamRadios(
        driverNumber: Int?, sessionKey: String?, meetingKey: String?
    ): Flow<List<TeamRadio>> = flow {
        while (coroutineContext.isActive) {
            val request: List<TeamRadio> = makeRequest("team_radio") {
                sessionKey?.let { parameters.append("session_key", it) }
                meetingKey?.let { parameters.append("meeting_key", it) }
                driverNumber?.let { parameters.append("driver_number", it.toString()) }
            }
            emit(request)
            delay(DELAY_TIME)
        }
    }

    override fun getWeather(
        sessionKey: String?, meetingKey: String?
    ): Flow<List<Weather>> = flow {
        while (coroutineContext.isActive) {
            val request: List<Weather> = makeRequest("weather") {
                sessionKey?.let { parameters.append("session_key", it) }
                meetingKey?.let { parameters.append("meeting_key", it) }
            }
            emit(request)
            delay(DELAY_TIME)
        }
    }


    private suspend inline fun <reified T> makeRequest(
        endpoint: String, crossinline block: URLBuilder.() -> Unit
    ): List<T> {
        try {
            val response = client.get("$BASE_URL/$endpoint") {
                url { block() }
                headers {
                    append(HttpHeaders.Accept, "application/json")
                    append(HttpHeaders.AcceptCharset, "UTF-8")
                    append(HttpHeaders.CacheControl, "no-cache")
                    append(HttpHeaders.Connection, "keep-alive")
                    append(HttpHeaders.UserAgent, "F1ApiClient/1.0")
                    append(HttpHeaders.AcceptLanguage, "en-US,en;q=0.9")
                    append(HttpHeaders.Pragma, "no-cache")
                }
            }
            if (response.status == HttpStatusCode.OK) {
                return response.body<List<T>>()
            } else {
                println("Error $response")
                return emptyList()
            }

        } catch (e: Exception) {
            println("Error al obtener los datos: ${e.message}")
            return emptyList()
        }

    }
}

