plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

group = "me.adzuki.activitylogger"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    // Expect runtime to have this
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
}

tasks.jar {
    manifest {
        attributes["FMLModType"] = "LIBRARY"
    }
}
