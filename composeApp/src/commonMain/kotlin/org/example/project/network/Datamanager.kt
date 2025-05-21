package org.example.project.network

import kotlinx.serialization.Serializable


@Serializable
data class DriverData(
    val nameAcronym: String,
    val teamColour: String
)

object ManualDriverData {
    val drivers = mapOf(
        1 to DriverData("VER", "3671C6"),
        22 to DriverData("TSU", "3671C6"),
        4 to DriverData("NOR", "FF8000"),
        81 to DriverData("PIA", "FF8000"),
        16 to DriverData("LEC", "E80020"),
        44 to DriverData("HAM", "E80020"),
        63 to DriverData("RUS", "27F4D2"),
        12 to DriverData("ANT", "27F4D2"),
        6 to DriverData("HAD", "6692FF"),
        30 to DriverData("LAW", "6692FF"),
        23 to DriverData("ALB", "64C4FF"),
        55 to DriverData("SAI", "64C4FF"),
        87 to DriverData("BEA", "B6BABD"),
        31 to DriverData("OCO", "B6BABD"),
        14 to DriverData("ALO", "229971"),
        18 to DriverData("STR", "229971"),
        10 to DriverData("GAS", "0093CC"),
        43 to DriverData("COL", "0093CC"),
        7 to DriverData("DOO", "0093CC"),
        5 to DriverData("BOR", "52E252"),
        27 to DriverData("HUL", "52E252"),

        )
}

private val SESSION_TYPES = mapOf(
    "FP1" to "Practice 1",
    "FP2" to "Practice 2",
    "FP3" to "Practice 3",
    "Q" to "Qualifying",
    "SQ" to "Sprint Qualifying",
    "S" to "Sprint",
    "R" to "Race"
)


enum class Circuitos(val nombreOficial: String, val pais: String, val codigoApi: String) {
    SAKHIR("Bahrain International Circuit", "Bahrain", "Sakhir"),
    JEDDAH("Jeddah Corniche Circuit", "Saudi Arabia", "Jeddah"),
    MELBOURNE("Albert Park Circuit", "Australia", "Melbourne"),
    SUZUKA("Suzuka International Racing Course", "Japan", "Suzuka"),
    SHANGHAI("Shanghai International Circuit", "China", "Shanghai"),
    MIAMI("Miami International Autodrome", "USA", "Miami"),
    IMOLA("Autodromo Enzo e Dino Ferrari", "Italy", "Imola"),
    MONACO("Circuit de Monaco", "Monaco", "Monte Carlo"),
    BARCELONA("Circuit de Barcelona-Catalunya", "Spain", "Catalunya"),
    MONTREAL("Circuit Gilles Villeneuve", "Canada", "Montreal"),
    SPIELBERG("Red Bull Ring", "Austria", "Spielberg"),
    SILVERSTONE("Silverstone Circuit", "United Kingdom", "Silverstone"),
    BUDAPEST("Hungaroring", "Hungary", "Hungaroring"),
    SPA("Circuit de Spa-Francorchamps", "Belgium", "Spa-Francorchamps"),
    ZANDVOORT("Circuit Zandvoort", "Netherlands", "Zandvoort"),
    MONZA("Autodromo Nazionale Monza", "Italy", "Monza"),
    BAKU("Baku City Circuit", "Azerbaijan", "Baku"),
    SINGAPORE("Marina Bay Street Circuit", "Singapore", "Singapore"),
    AUSTIN("Circuit of The Americas", "USA", "Austin"),
    MEXICO_CITY("Autódromo Hermanos Rodríguez", "Mexico", "Mexico City"),
    SAO_PAULO("Autódromo José Carlos Pace", "Brazil", "Interlagos"),
    LAS_VEGAS("Las Vegas Strip Circuit", "USA", "Las Vegas"),
    LUSAIL("Lusail International Circuit", "Qatar", "Lusail"),
    YAS_MARINA("Yas Marina Circuit", "UAE", "Yas Marina Circuit");

    override fun toString(): String {
        return codigoApi
    }
}