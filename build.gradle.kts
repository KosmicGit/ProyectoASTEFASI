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
    implementation("io.kweb:kweb-core:1.4.12")

    // This (or another SLF4J binding) is required for Kweb to log errors
    implementation("org.slf4j:slf4j-simple:2.0.3")

    implementation("com.mysql:mysql-connector-j:8.3.0")

    //Google GSON
    implementation("com.google.code.gson:gson:2.10.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
