import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm")
}

group = "ldgame"
version = "0.0.1"

repositories {

}

dependencies {
    implementation(project(":common"))
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("TP-Machine")
    archiveClassifier.set("")
}