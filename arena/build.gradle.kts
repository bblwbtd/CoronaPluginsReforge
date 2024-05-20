import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

version = "0.0.2"

dependencies {
    implementation(project(":common"))
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("Arena")
    archiveClassifier.set("")
}
