package org.example.project.presentation

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun formatDate(dateString: String): String {
    val outputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss")
    val isoFormatter = DateTimeFormatter.ISO_DATE_TIME

    return try {
        LocalDateTime.parse(dateString, isoFormatter).format(outputFormatter)
    } catch (_: Exception) {
        dateString
    }
}