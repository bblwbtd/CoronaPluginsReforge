plugins {
    kotlin("jvm")
}

group = "ldgame"
version = "0.0.1"


dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.7.0")
    implementation("org.ktorm:ktorm-core:3.4.1")
    implementation("com.mchange:c3p0:0.9.5.5")
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")
    implementation("org.flywaydb:flyway-core:8.5.0")
}

