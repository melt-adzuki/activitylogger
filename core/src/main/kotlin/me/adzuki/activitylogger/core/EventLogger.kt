package me.adzuki.activitylogger.core

import java.io.File
import java.util.Date

class EventLogger(
    val playerName: String = "<SYSTEM>",
    val eventName: String,
    val content: String = "",
    val position: PlayerPosition? = null,
    val dimensionId: String = "",
) : Logger() {
    override fun write() {
        val date = Date()
        val timestamp = timestampFormat.format(date)
        val currentDateString = dateFormat.format(date)

        File("activities_$currentDateString.csv").apply {
            if (!exists()) appendText("Timestamp,Player,Event,Content,Position,Dimension\n")
            appendText("$timestamp,$playerName,$eventName,${content.csvEscaped},${position?.toString()?.csvEscaped ?: ""},${dimensionId}\n")
        }
    }
}
