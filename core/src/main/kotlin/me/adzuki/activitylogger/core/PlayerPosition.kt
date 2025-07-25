package me.adzuki.activitylogger.core


data class PlayerPosition(val x: Double, val y: Double, val z: Double) {
    val intX get() = x.toInt()
    val intY get() = y.toInt()
    val intZ get() = z.toInt()

    override fun toString() = "($intX, $intY, $intZ)"
}
