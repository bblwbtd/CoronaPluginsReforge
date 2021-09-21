plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "ldgame"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":i18n"))
    implementation(project(":common"))
}


tasks {
    shadowJar {
        manifest {
            attributes(Pair("Main-Class", "MagicViewPlugin"))
        }
    }
}

