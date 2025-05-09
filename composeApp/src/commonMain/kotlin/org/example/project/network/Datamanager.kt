package org.example.project.network


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