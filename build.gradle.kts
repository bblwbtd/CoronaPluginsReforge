plugins {
    kotlin("jvm") version "1.9.20"
}

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
        compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
        testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
        implementation("com.github.ajalt.clikt:clikt:4.4.0")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.+")
    }

    tasks.test {
        useJUnitPlatform()
    }


}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
}