import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "ldgame"
version = "0.0.2"

dependencies {
    implementation(project(":common"))
    implementation(kotlin("stdlib-jdk8"))
}

repositories {
    mavenCentral()
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("CoronaTPMachine")
    archiveClassifier.set("")
}