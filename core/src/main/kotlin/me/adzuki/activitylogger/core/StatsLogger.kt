package me.adzuki.activitylogger.core

import java.io.File
import java.util.Date

class StatsLogger(val playerCount: Int) : Logger() {
    override fun write() {
        val timestamp = timestampFormat.format(Date())

        File("activities_statistics.csv").apply {
            if (!exists()) appendText("Timestamp,Players\n")
            appendText("$timestamp,$playerCount\n")
        }
    }
}
