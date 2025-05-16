plugins {
    kotlin("jvm") version "2.1.21"
    kotlin("plugin.serialization") version "2.1.21"
}

//group = "slekker"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.arrow-kt:arrow-core:2.1.1")
    implementation("io.arrow-kt:arrow-fx-coroutines:2.1.1")
}
tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}