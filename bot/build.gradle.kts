
group = "xyz.ldgame"
version = "unspecified"
val ktorVersion = "2.3.12"
val ktorm = object {
    val version = "0.7.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")
    testImplementation(kotlin("test"))
    implementation(project(":common"))
    implementation(project(":auth"))
}

tasks.test {
    useJUnitPlatform()
}