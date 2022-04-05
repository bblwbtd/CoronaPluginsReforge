import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.6.10"
}

group = "ldgame"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":common"))
    implementation(project(":tpmachine"))
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("CoronaFriends")
    archiveClassifier.set("")
}
