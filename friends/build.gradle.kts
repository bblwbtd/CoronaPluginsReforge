import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "ldgame"
version = "0.0.2"


dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":common"))
    implementation(project(":tpmachine"))
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("CoronaFriends")
    archiveClassifier.set("")
}
