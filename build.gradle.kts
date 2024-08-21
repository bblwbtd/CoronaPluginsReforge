import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.0.0"
    id("com.gradleup.shadow") version "8.3.0" apply false
}

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.gradleup.shadow")

    group = "ldgame"

    repositories {
        mavenCentral()

        maven {
            url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")

            // As of Gradle 5.1, you can limit this to only those
            // dependencies you expect from it
            content {
                includeGroup("org.bukkit")
                includeGroup("org.spigotmc")
            }
        }

        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }

    }

    dependencies {
        runtimeOnly(kotlin("reflect"))
        testImplementation(kotlin("test"))
        compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
        implementation("com.github.ajalt.clikt:clikt:4.4.0.9-SNAPSHOT")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.+")
    }

    tasks.test {
        useJUnitPlatform()
    }

    tasks.withType<ShadowJar> {
        minimize {
            exclude(dependency("org.jetbrains.kotlin:kotlin-reflect:.*"))
        }
    }
}

kotlin {
    jvmToolchain(17)
}