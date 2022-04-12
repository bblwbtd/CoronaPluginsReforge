import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm")
}

group = "ldgame"
version = "0.0.1"

dependencies {
    implementation(project(":common"))
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("TP-Machine")
    archiveClassifier.set("")
}