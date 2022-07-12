import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm")
}

group = "ldgame"
version = "0.0.1"


dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":common"))
    implementation(project(":tpmachine"))
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("CoronaFriends")
    archiveClassifier.set("")
}
