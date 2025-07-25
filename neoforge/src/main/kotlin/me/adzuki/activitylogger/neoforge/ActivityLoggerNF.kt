package me.adzuki.activitylogger.neoforge

import net.neoforged.api.distmarker.Dist
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.common.NeoForge

@Mod(value = ActivityLoggerNF.MOD_ID, dist = [Dist.DEDICATED_SERVER])
object ActivityLoggerNF {
    const val MOD_ID = "activitylogger"

    init {
        NeoForge.EVENT_BUS.register(EventHandler)
    }
}
