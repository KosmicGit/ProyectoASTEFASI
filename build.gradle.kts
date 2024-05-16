plugins {
    kotlin("jvm") version "1.9.23"
    id("org.jetbrains.dokka") version "1.9.20"
}

group = "es.cifpvirgen"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin Tests
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Kweb-Core
    implementation("io.kweb:kweb-core:1.4.12")

    // Kweb-logs
    implementation("org.slf4j:slf4j-simple:2.0.3")

    // Java Mail
    implementation("com.sun.mail:javax.mail:1.6.2")

    // MySql Connector J
    implementation("com.mysql:mysql-connector-j:8.3.0")

    //Google GSON
    implementation("com.google.code.gson:gson:2.10.1")

    // Dotenv Kotlin
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
