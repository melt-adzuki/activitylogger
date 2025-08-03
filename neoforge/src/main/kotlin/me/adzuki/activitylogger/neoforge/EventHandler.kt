package me.adzuki.activitylogger.neoforge

import com.mojang.logging.LogUtils
import me.adzuki.activitylogger.core.EventLogger
import me.adzuki.activitylogger.core.PlayerInfoLogger
import me.adzuki.activitylogger.core.PlayerPosition
import me.adzuki.activitylogger.core.StatsLogger
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.ServerChatEvent
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent
import net.neoforged.neoforge.event.entity.player.AdvancementEvent
import net.neoforged.neoforge.event.entity.player.PlayerEvent
import net.neoforged.neoforge.event.server.ServerStartingEvent
import net.neoforged.neoforge.event.server.ServerStoppingEvent
import org.slf4j.Logger
import kotlin.jvm.optionals.getOrNull

@EventBusSubscriber(modid = MOD_ID)
object EventHandler {
    private val logger: Logger = LogUtils.getLogger()

    private inline val Player.position get() = PlayerPosition(x, y, z)
    private inline val Player.dimensionId get() = level().dimension().location().toString()

    @JvmStatic
    @SubscribeEvent
    fun onServerStarting(event: ServerStartingEvent) {
        logger.info("ActivityLogger is running!")
        EventLogger(eventName = "Start").write()
    }

    @JvmStatic
    @SubscribeEvent
    fun onServerStopping(event: ServerStoppingEvent) {
        EventLogger(eventName = "Stop").write()
    }

    @JvmStatic
    @SubscribeEvent
    fun onPlayerJoin(event: PlayerEvent.PlayerLoggedInEvent) {
        val player = event.entity as? ServerPlayer ?: return
        val playerName = player.name.string
        val server = player.server

        EventLogger(
            eventName = "Join",
            playerName = playerName,
            position = player.position,
            dimensionId = player.dimensionId,
        ).write()
        StatsLogger(playerCount = server.playerCount).write()
        PlayerInfoLogger(
            playerName = playerName,
            playerIP = player.ipAddress,
            playerUUID = player.uuid,
        ).write()
    }

    @JvmStatic
    @SubscribeEvent
    fun onPlayerLeave(event: PlayerEvent.PlayerLoggedOutEvent) {
        val player = event.entity as? ServerPlayer ?: return
        val server = player.server

        EventLogger(
            eventName = "Leave",
            playerName = player.name.string,
            position = player.position,
            dimensionId = player.dimensionId,
        ).write()
        StatsLogger(playerCount = server.playerCount - 1).write()
    }

    @JvmStatic
    @SubscribeEvent
    fun onPlayerChat(event: ServerChatEvent) {
        val player = event.player
        val chatMessage = event.message.string

        EventLogger(
            eventName = "Chat",
            playerName = player.name.string,
            content = chatMessage,
            position = player.position,
            dimensionId = player.dimensionId,
        ).write()
    }

    @JvmStatic
    @SubscribeEvent
    fun onAdvancementEarn(event: AdvancementEvent.AdvancementEarnEvent) {
        if (event.advancement.value.isRoot) return

        val player = event.entity
        val advancementName = event.advancement.value.display.getOrNull()?.title?.string
            ?: event.advancement.id.toString()

        EventLogger(
            eventName = "Advancement",
            playerName = player.name.string,
            content = advancementName,
            position = player.position,
            dimensionId = player.dimensionId,
        ).write()
    }

    @JvmStatic
    @SubscribeEvent
    fun onPlayerDeath(event: LivingDeathEvent) {
        val player = event.entity as? Player ?: return
        val deathMessage = event.source.getLocalizedDeathMessage(player).string

        EventLogger(
            eventName = "Death",
            playerName = player.name.string,
            content = deathMessage,
            position = player.position,
            dimensionId = player.dimensionId,
        ).write()
    }
}
