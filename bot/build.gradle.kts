
group = "xyz.ldgame"
version = "unspecified"
val ktorVersion = "2.3.12"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    testImplementation(kotlin("test"))
    implementation(project(":common"))
    implementation(project(":auth"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}