package me.adzuki.activitylogger.core

import kotlin.math.roundToInt

data class PlayerPosition(val x: Double, val y: Double, val z: Double) {
    val rx get() = x.roundToInt()
    val ry get() = y.roundToInt()
    val rz get() = z.roundToInt()

    override fun toString() = "($rx, $ry, $rz)"
}
