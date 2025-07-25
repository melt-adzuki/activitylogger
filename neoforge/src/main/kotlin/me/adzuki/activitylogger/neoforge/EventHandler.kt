package me.adzuki.activitylogger.neoforge

import com.mojang.logging.LogUtils
import me.adzuki.activitylogger.core.EventLogger
import me.adzuki.activitylogger.core.PlayerInfoLogger
import me.adzuki.activitylogger.core.PlayerPosition
import me.adzuki.activitylogger.core.StatsLogger
import net.minecraft.world.entity.player.Player
import net.minecraft.world.phys.Vec3
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.ServerChatEvent
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent
import net.neoforged.neoforge.event.entity.player.PlayerEvent
import net.neoforged.neoforge.event.server.ServerStartingEvent
import net.neoforged.neoforge.event.server.ServerStoppingEvent
import org.slf4j.Logger

internal object EventHandler {
    private val logger: Logger = LogUtils.getLogger()
    private val Vec3.playerPosition get() = PlayerPosition(x, y, z)

    @SubscribeEvent
    fun onServerStarting(event: ServerStartingEvent) {
        logger.info("ActivityLogger is running!")
        EventLogger(eventName = "Start").write()
    }

    @SubscribeEvent
    fun onServerStopping(event: ServerStoppingEvent) {
        EventLogger(eventName = "Stop").write()
    }

    @SubscribeEvent
    fun onPlayerJoin(event: PlayerEvent.PlayerLoggedInEvent) {
        val player = event.entity
        val playerName = player.name.string
        val server = player.server!!

        EventLogger(
            eventName = "Join",
            playerName = playerName,
            position = player.position().playerPosition,
        ).write()
        StatsLogger(playerCount = server.playerCount).write()
        PlayerInfoLogger(
            playerName = playerName,
            playerIP = server.playerList.getPlayer(player.uuid)!!.ipAddress,
            playerUUID = player.uuid,
        ).write()
    }

    @SubscribeEvent
    fun onPlayerLeave(event: PlayerEvent.PlayerLoggedOutEvent) {
        val player = event.entity
        val server = player.server!!

        EventLogger(
            eventName = "Leave",
            playerName = player.name.string,
            position = player.position().playerPosition,
        ).write()
        StatsLogger(playerCount = server.playerCount - 1).write()
    }

    @SubscribeEvent
    fun onPlayerChat(event: ServerChatEvent) {
        EventLogger(
            eventName = "Chat",
            playerName = event.player.name.string,
            content = event.message.string,
            position = event.player.position().playerPosition,
        ).write()
    }

    @SubscribeEvent
    fun onPlayerDeath(event: LivingDeathEvent) {
        val player = event.entity as? Player ?: return
        val deathMessage = event.source.getLocalizedDeathMessage(player).string

        EventLogger(
            eventName = "Death",
            playerName = player.name.string,
            content = deathMessage,
            position = player.position().playerPosition,
        ).write()
    }
}
