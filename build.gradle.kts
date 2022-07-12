import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
}

group = "ldgame"
version = "0.0.1"

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

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

    }

    dependencies {
        testImplementation(kotlin("test"))
        implementation(kotlin("stdlib-jdk8"))
        compileOnly("org.spigotmc:spigot-api:1.18.1-R0.1-SNAPSHOT")
        testImplementation("com.github.seeseemelk:MockBukkit-v1.17:1.13.0")
        testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
        implementation("com.github.ajalt.clikt:clikt:3.5.0")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
    }

    tasks.test {
        useJUnitPlatform()
    }

    val compileKotlin: KotlinCompile by tasks
    compileKotlin.kotlinOptions {
        jvmTarget = "16"
    }
}