plugins {
    kotlin("jvm") version "1.9.23"
}

group = "es.cifpvirgen"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("io.kweb:kweb-core:1.4.11")

    // This (or another SLF4J binding) is required for Kweb to log errors
    implementation("org.slf4j:slf4j-simple:2.0.3")

    implementation("mysql:mysql-connector-java:8.0.33")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
