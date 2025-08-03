package me.adzuki.activitylogger.neoforge

import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod

const val MOD_ID = "activitylogger"

@Mod(value = MOD_ID, dist = [Dist.DEDICATED_SERVER])
class ActivityLoggerNF(modBus: IEventBus, container: ModContainer)
