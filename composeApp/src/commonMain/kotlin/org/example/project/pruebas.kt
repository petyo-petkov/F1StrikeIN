package org.example.project

import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.first
import org.example.project.network.F1ApiClient


suspend fun fetchAndPrintDrivers() {
    val f1ApiClient = F1ApiClient(client = HttpClient())

    val result = f1ApiClient.getLaps().first()

    println("RESULTADO $result")
}



