package me.adzuki.activitylogger.core

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import java.util.UUID

class PlayerInfoLogger(
    val playerName: String,
    val playerIP: String,
    val playerUUID: UUID,
) : Logger() {
    @Serializable
    private data class PlayerInfo(
        val ips: MutableSet<String> = mutableSetOf(),
        val uuids: MutableSet<String> = mutableSetOf(),
    )

    override fun write() {
        val file = File("activities_players.json")
        val json = Json { prettyPrint = true }

        val infoKV: MutableMap<String, PlayerInfo> =
            if (file.exists()) json.decodeFromString(file.readText())
            else mutableMapOf()

        infoKV.getOrPut(playerName) { PlayerInfo() }
            .apply {
                ips.add(playerIP)
                uuids.add(playerUUID.toString())
            }

        file.writeText(json.encodeToString(infoKV))
    }
}
