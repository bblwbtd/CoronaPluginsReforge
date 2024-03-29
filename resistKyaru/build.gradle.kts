import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "ldgame"
version = "0.0.1"


dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":common"))
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("CoronaResistKyaru")
    archiveClassifier.set("")
}
