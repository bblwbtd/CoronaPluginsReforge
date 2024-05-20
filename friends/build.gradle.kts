import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

version = "0.0.2"


dependencies {
    implementation(project(":common"))
    implementation(project(":tpmachine"))
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("CoronaFriends")
    archiveClassifier.set("")
}
